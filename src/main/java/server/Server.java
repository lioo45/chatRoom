package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.*;

/**
 * Created by pro on 17/3/26.
 */
public class Server {

    private static final int DEFAULT_PORT=9999;

    //缓冲区的默认大小
    private static final int BUFFER_SIZE=1024;


//    private static Set<SelectionKey> selectionKeys=new HashSet<SelectionKey>();

    private static KeyHandler keyHandler=null;



    public static void main(String[] args) {
        ServerSocketChannel serverChannel;
        Selector selector = null;

        //register channel to listen localhost:DEFAULT_PORT
        try {
            //打开一个服务器信道
            serverChannel = ServerSocketChannel.open();
            //打开一个服务器socket绑定指定端口,并设置为非阻塞模式
            ServerSocket ss = serverChannel.socket();
            ss.bind(new InetSocketAddress(DEFAULT_PORT));
            serverChannel.configureBlocking(false);
            //打开一个selector
            selector = Selector.open();
            //注册该信道,交给selector托管
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyHandler = new KeyHandler(selector);

        while (true) {
            try {
                //由selector来轮询,是否有channels感兴趣的事件到来
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            //将所有感兴趣的时间封装在一个Iterator里,方便遍历
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //获取感兴趣的事件
                SelectionKey key = iterator.next();
                //会从对应的selector中移除该事件
                iterator.remove();
                keyHandler.deal(key);
                System.out.println("执行完一次select!!!");
            }
        }
    }
}
