package server;

import lwz.pojo.User;
import lwz.service.UserService;
import utils.SpringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
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

    UserService service= (UserService) SpringUtil.getBean(UserService.class);

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
            String params[]=message.split("\n");
            User user=new User(params[1],params[2],params[3]);
            String result=service.addUser(user)>0?"ok\0":"failure\0";
            write(result,key);
            return ;
        }else if(message.contains("login!")){
            //用户登录
            String params[]=message.split("\n");
            String result;
            if(params==null||params.length<3) {
                result="用户名密码不能为空";
            }
            else {
                result = service.login(params[1], params[2]);
                System.out.println(result);
            }
            userSelectionKeys.put(params[1],key);
            keyToUser.put(key,params[1]);
            write(result,key);
            return ;
        }else if(message.contains("message:121!")){

        }else if(message.contains("message:12n!")){

        }
        //广播信息
//        for (SelectionKey k : selectionKeys) {
//           write(message,k);
//        }
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
