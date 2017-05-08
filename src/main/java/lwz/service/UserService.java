package lwz.service;

import lwz.mapper.ChatRoomMapper;
import lwz.mapper.UserChatroomRelaMapper;
import lwz.mapper.UserMapper;
import lwz.mapper.UserRelationshipMapper;
import lwz.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pro on 17/5/6.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRelationshipMapper urMapper;

    @Autowired
    private UserChatroomRelaMapper ucrMapper;

    @Autowired
    private ChatRoomMapper crMapper;

    public int addUser(User user){
        return userMapper.insert(user);
    }

    public String login(String username,String password){
        UserExample example=new UserExample();
        UserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        List<User> list=userMapper.selectByExample(example);
        if(list==null||list.size()==0)
            return "login:用户名或者密码错误\n";
        String result="login:ok!\n"+list.get(0).getUid()+"&"+list.get(0).getNickname();
        return result;
    }

//    public User getUserById(int uid){
//        UserExample example=new UserExample();
//    }

    public List<User> getFriends(int uid){
        System.out.println("uid: "+uid);
        UserRelationshipExample example=new UserRelationshipExample();
        UserRelationshipExample.Criteria criteria=example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<UserRelationshipKey> list=urMapper.selectByExample(example);
        if(list==null||list.size()<=0)
            return null;
        List<Integer> friendIds=new ArrayList<Integer>(list.size());
        for(UserRelationshipKey key:list)
            friendIds.add(key.getFriendid());

        UserExample userExample=new UserExample();
        UserExample.Criteria uc=userExample.createCriteria();
        uc.andUidIn(friendIds);
        List<User> users=userMapper.selectByExample(userExample);

        return users;
    }

    public List<ChatRoom> getChatRooms(int uid){
        UserChatroomRelaExample example=new UserChatroomRelaExample();
        UserChatroomRelaExample.Criteria criteria=example.createCriteria();
        criteria.andUidEqualTo(uid);

        List<UserChatroomRelaKey> list=ucrMapper.selectByExample(example);

        if(list==null||list.size()<=0)
            return null;
        List<Integer> ctids=new ArrayList<Integer>(list.size());
        for (int i = 0; i <list.size() ; i++) {
            ctids.add(list.get(i).getCtid());
        }


        ChatRoomExample cre=new ChatRoomExample();
        ChatRoomExample.Criteria c=cre.createCriteria();
        c.andCtidIn(ctids);
        List<ChatRoom> rooms=crMapper.selectByExample(cre);

        return rooms;
    }
}
