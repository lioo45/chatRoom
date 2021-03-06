package client.GUI;


import lwz.pojo.ChatRoom;
import lwz.pojo.User;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author pro
 */
public class LoginAtferMainGUI extends javax.swing.JFrame {

    private GUIManager manager;
    /**
     * Creates new form LoginAtferMainGUI
     */
    public LoginAtferMainGUI() {
        initComponents();
    }
    public LoginAtferMainGUI(GUIManager manager) {
        this.manager=manager;
//        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">

    public void showFriend(String response){
        String result[]=response.split("\n");
        if(result.length>1) {
            for (int i = 1; i <result.length; i++) {
                String[] params=result[i].split(",");
                manager.getFriends().add(new User(new Integer(params[0]),params[1]));
            }
        }
        System.out.println(manager.getFriends().size());

        jList1.setModel(new javax.swing.AbstractListModel<User>() {
            public int getSize() { return manager.getFriends().size(); }
            public User getElementAt(int i) { return manager.getFriends().get(i); }
        });
        //初始化聊天室列表
        jLabel2.setText("我的聊天室");
        String getChatRoomHeader="get:chatroom!\0"+manager.getUid();
        manager.getClientSocket().write(getChatRoomHeader);
    }

    public void showChatRoom(String response){
        String[] roomsInfo=response.split("\n");

//        System.out.println(roomsInfo);

        if(roomsInfo.length>1) {
            for (int i = 1; i <roomsInfo.length; i++) {
                String[] params=roomsInfo[i].split(",");
                manager.getChatRooms().add(new ChatRoom(new Integer(params[0]),params[1],params[2]));
            }
        }

        jList2.setModel(new AbstractListModel<ChatRoom>() {
            @Override
            public int getSize() {
                return manager.getChatRooms().size();
            }

            @Override
            public ChatRoom getElementAt(int index) {
                return manager.getChatRooms().get(index);
            }
        });
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        friends = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<User>();
        jLabel2 = new javax.swing.JLabel();
        chatrooms = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<ChatRoom>();
        userInfo = new javax.swing.JLabel();

        userInfo.setText(manager.getNickname());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //初始化好友列表
        jLabel1.setText("我的好友");
        String getFriendHeader="get:friends!\0"+manager.getUid();
        manager.getClientSocket().write(getFriendHeader);
//        String result[]=manager.getClientSocket().read().split("\n");


        jList1.setToolTipText("");
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        friends.setViewportView(jList1);

//        String[] roomsInfo=manager.getClientSocket().read().split("\n");




        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        chatrooms.setViewportView(jList2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(friends, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                                                .addComponent(chatrooms, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                        .addComponent(userInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(userInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(friends, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chatrooms, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }

    public void go(){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        initComponents();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginAtferMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginAtferMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginAtferMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginAtferMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:

        ListModel listModel=jList1.getModel();
        User user= (User) listModel.getElementAt(jList1.getSelectedIndex());
        String uid=String.valueOf(user.getUid());
        ChatWithFriendGUI cwfg=manager.getFriendGUI(uid);
        if(cwfg==null){
            manager.addFriendGUI(uid,user.getNickname());
            cwfg=manager.getFriendGUI(uid);
            cwfg.go();
        }
        //获取聊天记录
        if(!hadGetRecord) {
            hadGetRecord=true;
            String header = "get:message 121!\0" + manager.getUid() + "&" + uid;
            manager.getClientSocket().write(header);
        }
        cwfg.setVisible(true);
    }


    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        ListModel<ChatRoom> listModel=jList2.getModel();
        ChatRoom cr= listModel.getElementAt(jList2.getSelectedIndex());
//        System.out.println(cr.getName()+": cr");
        String header="header:enter room!\0"+manager.getUid()+"&"+cr.getCtid();
        ChatRoomGUI crg=manager.getChatRoomGUI(cr.getCtid().toString());
        if(crg==null) {
            manager.addChatRoomGUI(cr.getCtid().toString());
            crg = manager.getChatRoomGUI(cr.getCtid().toString());
            crg.setName("用户: " + manager.getNickname() + " 聊天室:" + cr.getName());
            crg.go();
        }
        crg.setVisible(true);
        manager.getClientSocket().write(header);
//        System.out.println(listModel.getElementAt(jList2.getSelectedIndex()).toString());
    }

    // Variables declaration - do not modify
    private javax.swing.JScrollPane chatrooms;
    private javax.swing.JScrollPane friends;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<User> jList1;
    private javax.swing.JList<ChatRoom> jList2;
    private javax.swing.JLabel userInfo;

    private boolean hadGetRecord=false;
    // End of variables declaration
//
//    private String response;
//
//    public String getResponse() {
//        return response;
//    }
//
//    public void setResponse(String response) {
//        this.response = response;
//    }
}

