package server;

import lwz.pojo.ChatRoom;
import lwz.pojo.RecordChatroom;
import lwz.pojo.RecordUser;
import lwz.pojo.User;
import lwz.service.RecordService;
import lwz.service.UserService;
import utils.DateUtil;
import utils.SpringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * Created by pro on 17/5/5.
 */
public class KeyHandler {

    private Selector selector;

    private  Map<String,SelectionKey> userSelectionKeys=new HashMap<String, SelectionKey>();

    private  Map<SelectionKey,String> keyToUser=new HashMap<SelectionKey, String>();

    private UserService service= (UserService) SpringUtil.getBean(UserService.class);

    private RecordService recordService= (RecordService) SpringUtil.getBean(RecordService.class);


    private Map<String,Set<String>> getUidByRoom=new HashMap<String, Set<String>>();

    private Map<String,String> userInfo=new HashMap<String, String>();

//    private Set<String> hadGet=new HashSet<String>();


    private final int BUFFER_SIZE=4;

//    private Map<String,Set<String>>


    public KeyHandler(Selector selector) {
        this.selector = selector;
    }

    public void deal(SelectionKey key){
        try {
            if(key.isAcceptable()){
                System.out.println("is accpt");
                dealAccept(key);
            }else if(key.isReadable()) {
                System.out.println("is read");
                dealRead(key);
            }else if(key.isWritable()){
                System.out.println("is write");
              dealWrite(key);
            }
        }catch (IOException e) {
//            e.printStackTrace();
            key.cancel();
            String uid=keyToUser.get(key);
            userSelectionKeys.remove(uid);
            keyToUser.remove(key);
//            hadGet.remove(uid);
            try {
                key.channel().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private  void dealAccept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();
        System.out.println("接收了一个客户端连接: " + client);
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUFFER_SIZE));

    }

    private  void dealRead(SelectionKey key) throws IOException {
        //获取信道有关的附件 即缓冲区.
        // System.out.println("is readable");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer readbuf = (ByteBuffer) key.attachment();
        //将buf的position归零,令limit=capacity,以便可读
//        StringBuilder message = new StringBuilder();
        ArrayList<Byte> list=new ArrayList<Byte>();
        while (true) {
            readbuf.clear();
            int len = client.read(readbuf);
            if (len <= 0)
                break;
            for (int i = 0; i <readbuf.position() ; i++) {
                list.add(readbuf.get(i));
            }
        }
        byte[] bytes=new byte[list.size()];
        for (int i = 0; i <bytes.length ; i++) {
            bytes[i]=list.get(i);
        }
        String message=new String(bytes,"UTF-8");

        if (message.isEmpty()) {
            System.out.println("客户端退出: " + key.channel());
            throw new IOException();
        }
        //用户注册
        if(message.contains("register!")){
            String params[]=message.split("\0");
            User user=new User(params[1],params[2],params[3]);
            String header="register:";
            String result=header+(service.addUser(user)>0?"ok":"failure")+"\n";
            write(result,key);
            return ;
        }else if(message.contains("login!")){
            //用户登录
            String params[]=message.split("\0")[1].split("&");
            String result;
            if(params==null||params.length<2) {
                result="login:用户名密码不能为空\n";
            }
            else {
                result = service.login(params[0], params[1]);
//                System.out.println(result);
                if(result.contains("ok")) {
                    String uid = result.split("\n")[1].split("&")[0];
                    String nickname = result.split("\n")[1].split("&")[1];
                    userInfo.put(uid, nickname);
                    userSelectionKeys.put(uid, key);
                    keyToUser.put(key, uid);
                }
            }
            write(result,key);
            return ;
        }else if(message.contains("get:friends!")){
            String params[]=message.split("\0");
//            System.out.println(message);
            System.out.println("param: "+params[1]);
            List<User> friends=service.getFriends(new Integer(params[1]));
            StringBuilder sb=new StringBuilder();
            sb.append("get:friend ok!\n");
            if(friends!=null) {
//                sb.append(friends.size() + "\n");
                for (int i = 0; i < friends.size(); i++) {
                    User f = friends.get(i);
                    sb.append(f.getUid() + "," + f.getNickname() + "\n");
                }
            }
            write(sb.toString(),key);
        }else if(message.contains("get:chatroom!")){
            String params[]=message.split("\0")[1].split("&");
            List<ChatRoom> rooms=service.getChatRooms(new Integer(params[0]));
            StringBuilder sb=new StringBuilder();
            sb.append("get:chatroom ok!\n");
            if(rooms!=null) {
                for (int i = 0; i < rooms.size(); i++) {
                    ChatRoom cr = rooms.get(i);
                    sb.append(cr.getCtid() + "," + cr.getName() + "," + cr.getDescription() + "\n");
                }
            }
            write(sb.toString(),key);
        } else if(message.contains("message:121!")){
//            String header=message.substring(0,message.indexOf("\0"));
            String info=message.substring(message.indexOf("\0")+1);
            String params[]=info.substring(0,info.indexOf("\0")).split("&");
            info=info.substring(info.indexOf("\0")+1);
            String uid=params[0];
            String friendId=params[1];
            SelectionKey key1=userSelectionKeys.get(friendId);
            String responseHeaderOfSender="message:121\n"+uid+"&"+friendId+"\n";
            String responseHeaderOfReceiver="message:121\n"+friendId+"&"+uid+"\n";

            String responseInfo=DateUtil.getNowDate()+"\n"+info+"\n";
            //回显给发送方
            write(responseHeaderOfSender+responseInfo,key);
            //回显给好友
            write(responseHeaderOfReceiver+responseInfo,key1);

            //聊天记录写入数据库
            recordService.addRecord(new RecordUser(new Integer(uid),
                    new Integer(friendId),responseInfo,new Date()));

        } else if(message.contains("message:12n!")){
//            System.out.println(message);
            String header=message.substring(0,message.indexOf("\0"));
            String info=message.substring(message.indexOf("\0")+1);
            String params[]=info.substring(0,info.indexOf("\0")).split("&");

            info=info.substring(info.indexOf("\0")+1);

//            System.out.println(header);
//            System.out.println(info);
            //广播
            String ctid=params[1];
            Set<String> uids=getUidByRoom.get(ctid);
            String responeHeader="message:12n\n"+params[0]+"&"+params[1]+"\n";
            for(String uid:uids){
                SelectionKey k=userSelectionKeys.get(uid);
                if(k!=null) {
                    String date=DateUtil.getNowDate();
                    recordService.addRecord(
                            new RecordChatroom(new Integer(ctid),new Integer(uid),date+"\n"+info+"\n",new Date()));

                    write(responeHeader + date + "\n" + info + "\n", k);
                }
                else {
                    uids.remove(uid);
                    System.out.println("用户断开连接,Uid: "+uid);
                }
            }

        }else if(message.contains("header:enter room!")){

            String[] params=message.substring(message.indexOf("\0")+1).split("&");
            String ctid=params[1];
            String uid=params[0];
            Set<String> set=getUidByRoom.get(params[1]);
//            System.out.println("roomid:"+params[1]);
            if(set==null) {
                set = new HashSet<String>();
                getUidByRoom.put(params[1],set);
            }
            if(!set.contains(uid)) {
                set.add(uid);
                //回显聊天记录
                List<RecordChatroom> records =
                        recordService.getRecordsOrderByDate(new Integer(ctid));
                if(records!=null) {
                    StringBuilder sb = new StringBuilder("header:record room!\n");
                    sb.append(ctid + "\n");
                    for (RecordChatroom record : records) {
                        sb.append(record.getRecord() + "\n");
                    }
                    System.out.println(sb.toString());
                    write(sb.toString(), key);
                    dealWrite(key);
                }
                StringBuilder people=new StringBuilder();
                //回显聊天时的在线情况
                for(String uid1:set){
                    people.append(uid1+": "+userInfo.get(uid1)+"\n");
                }
                String header="header:room people!\n"+ctid+"\n";
                for(String uid2:getUidByRoom.get(ctid)) {
                    SelectionKey k1=userSelectionKeys.get(uid2);
                    write(header + people.toString(), k1);
                }
            }


        }else if(message.contains("header:exit room!")){
            String[] params=message.split("\0")[1].split("&");
            String uid=params[0];
            String ctid=params[1];
            //在该聊天室移除相应的用户.
            getUidByRoom.get(ctid).remove(uid);

        }else if(message.contains("get:message 121")){
            //获取好友聊天记录
            String params[]=message.substring(message.indexOf("\0")+1).split("&");
            StringBuilder sb = new StringBuilder();
            List<RecordUser> records = recordService.getUserRecord(params[0], params[1]);
            String responseHeader = "message:121 \n" + params[0] + "&" + params[1] + "\n";
            if (records != null) {
                for (RecordUser r : records) {
                    System.out.println("聊天记录:" + r.getRecord());
                    sb.append(r.getRecord() + "\n");
                }
            }
            write(responseHeader + sb.toString(), key);
        }

    }

    private  void dealWrite(SelectionKey key) throws IOException {
        key.interestOps(key.interestOps()&~SelectionKey.OP_WRITE);
        SocketChannel client= (SocketChannel) key.channel();
        ByteBuffer buf= (ByteBuffer) key.attachment();
        client.write(buf);
        buf.compact();
    }


    private void write(String info,SelectionKey key) throws UnsupportedEncodingException {
        info+="\0";
        System.out.println(key.channel()+" :"+info);
        //转码成utf-8
        byte[] messageByte=info.getBytes("UTF-8");
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //如果缓冲区放不下要发送的信息则重新分配缓冲区
        if(messageByte.length>buf.capacity())
            buf=ByteBuffer.allocate(messageByte.length);
        buf.clear();
        buf.put(messageByte);
        buf.flip();
        key.attach(buf);
        key.interestOps(key.interestOps()|SelectionKey.OP_WRITE);
    }
}
