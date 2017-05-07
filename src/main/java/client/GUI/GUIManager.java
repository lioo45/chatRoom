package client.GUI;

import client.socket.ClientSocket;

/**
 * Created by pro on 17/5/6.
 */
public class GUIManager {

    private ClientSocket clientSocket;


    private   LoginGUI loginGUI=new LoginGUI(this);

    private   RegisterGUI registerGUI=new RegisterGUI(this);

    private   ChatRoomGUI chatRoomGUI=new ChatRoomGUI(this);

    private   ErrorGUI errorGUI=new ErrorGUI(this);

    private   LoginAtferMainGUI loginAtferMainGUI=new LoginAtferMainGUI(this);

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

    public  ChatRoomGUI getChatRoomGUI() {
        return chatRoomGUI;
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
}
