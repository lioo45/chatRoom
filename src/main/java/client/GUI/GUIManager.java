package client.GUI;

import client.socket.ClientSocket;
import lwz.pojo.ChatRoom;
import lwz.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pro on 17/5/6.
 */
public class GUIManager {

    private ClientSocket clientSocket;

    private LoginGUI loginGUI = new LoginGUI(this);

    private RegisterGUI registerGUI = new RegisterGUI(this);


    private ErrorGUI errorGUI = new ErrorGUI(this);

    private LoginAtferMainGUI loginAtferMainGUI = new LoginAtferMainGUI(this);

    private Map<String,ChatRoomGUI> chatRoomGUIMap=new HashMap<String, ChatRoomGUI>();

    private Map<String,ChatWithFriendGUI> chatWithFriendGUIMap=new HashMap<String, ChatWithFriendGUI>();


    private List<User> friends=new ArrayList<User>();

    private List<ChatRoom> chatRooms=new ArrayList<ChatRoom>();

    private String uid;
    private String nickname;

    public GUIManager(ClientSocket clientSocket) {
        this.clientSocket=clientSocket;
    }

    public  LoginGUI getLoginGUI() {
        return loginGUI;
    }

    public  RegisterGUI getRegisterGUI() {
        return registerGUI;
    }

    public  ChatRoomGUI getChatRoomGUI(String ctid) {
        return chatRoomGUIMap.get(ctid);
    }

    public  ErrorGUI getErrorGUI() {
        return errorGUI;
    }

    public  LoginAtferMainGUI getLoginAtferMainGUI() {
        return loginAtferMainGUI;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void addChatRoomGUI(String ctid){
        ChatRoomGUI gui=getChatRoomGUI(ctid);
        if(gui==null)
            gui=new ChatRoomGUI(this);
        gui.setCtid(ctid);
        chatRoomGUIMap.put(ctid,gui);
    }

    public void addFriendGUI(String friendID,String nickname){
        ChatWithFriendGUI gui=getFriendGUI(friendID);
        if(gui==null)
            gui=new ChatWithFriendGUI(this);
        gui.setFriendID(friendID);
        gui.setNickName(nickname);
        gui.setName("好友: "+nickname);
        gui.setTitle("好友: "+nickname);
        chatWithFriendGUIMap.put(friendID,gui);
    }

    public ChatWithFriendGUI getFriendGUI(String friendID){
        return chatWithFriendGUIMap.get(friendID);
    }


    public void getResponse(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String result = clientSocket.read();
                    String[] s = result.split("\n");
                    String header = s[0];
                    if (header.contains("register:")) {
                        registerGUI.callback(result);
                    } else if (header.contains("login:")) {
                        loginGUI.callback(result);
                    } else if (header.contains("get:friend")) {
                        loginAtferMainGUI.showFriend(result);
                    } else if (header.contains("get:chatroom")) {
                        loginAtferMainGUI.showChatRoom(result);
                    } else if (header.contains("message:12n")) {
                        String[] params = s[1].split("&");
                        ChatRoomGUI gui = chatRoomGUIMap.get(params[1]);
                        gui.callback(result);
                    } else if(header.contains("message:121")){
//                        String uid1=s[1].split("&")[0];
                        String friendID=s[1].split("&")[1];
                        ChatWithFriendGUI gui = chatWithFriendGUIMap.get(friendID);
                        gui.showInfo(result);
                    } else if(header.contains("header:record room!")){
                        System.out.println("show record");
                        chatRoomGUIMap.get(s[1]).showRecord(result);
                    }else if(header.contains("header:room people!")){
                        chatRoomGUIMap.get(s[1]).showPeople(s);
                    }
                }
            }
        }).start();
    }

}
