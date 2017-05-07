package client.socket;



import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by pro on 17/3/26.
 */
public class ClientSocket {


    private  final int TIME_OUT=10000;

    private Socket socket;

    private String ipAddr="127.0.0.1";

    private  int DEFAULT_PORT=9999;


    private void init(){
    }

    public ClientSocket(String name) {

    }


    public  Socket createConnect() {
        try {
            socket=new Socket(ipAddr,DEFAULT_PORT);
            socket.setSoTimeout(TIME_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public  Socket getSocket(){
        if(socket==null||socket.isClosed())
            createConnect();
        return socket;
    }

    public  void write(String info){
        socket=getSocket();
        try {
            System.out.println("send:  "+info);
            byte[] bytes=info.getBytes("UTF-8");
            OutputStream out=socket.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if(!socket.isConnected()||socket.isClosed()) {
                socket=null;
            }
        }
    }

    public  String read() {
        ArrayList<Byte> list = new ArrayList<Byte>(1024);
        socket = getSocket();
        while (true) {
            list.clear();
            try {
                InputStream in = socket.getInputStream();
                for (int i = in.read(); i > 0 && i != -1; i = in.read()) {
                    char c = (char) i;
                    if (c == '\0')
                        break;
                    list.add((byte) c);

                }
                //转码
                if (list.size() > 0) {
                    byte[] bytes = new byte[list.size()];
                    for (int i = 0; i < bytes.length; i++)
                        bytes[i] = list.get(i);
                    String sb = new String(bytes, "UTF-8");
                    return sb;
                }
//                System.out.println(sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static String read(){
//        try {
//            StringBuilder sb=new StringBuilder();
//            InputStream in=socket.getInputStream();
//            for (int i = in.read(); i >0 ; i++) {
//                char c= (char) i;
//                System.out.print(c);
//                Thread.sleep(500);
//                if(c=='\0')
//                    break;
//                sb.append(c);
//            }
//            return sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }


}
