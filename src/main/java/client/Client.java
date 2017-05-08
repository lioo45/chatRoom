package client;

import client.GUI.GUIManager;
import client.socket.ClientSocket;


/**
 * Created by pro on 17/3/26.
 */
public class Client{

    public static void main(String[] args) {
        ClientSocket clientSocket=new ClientSocket("QQ");
        GUIManager manager=new GUIManager(clientSocket);
        manager.getResponse();
        manager.getLoginGUI().go();
    }


}
