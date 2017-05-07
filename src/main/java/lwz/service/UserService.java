package lwz.service;

import lwz.mapper.UserMapper;
import lwz.pojo.User;
import lwz.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pro on 17/5/6.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper mapper;


    public int addUser(User user){
        return mapper.insert(user);
    }

    public String login(String username,String password){
        UserExample example=new UserExample();
        UserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        List<User> list=mapper.selectByExample(example);
        if(list==null||list.size()==0)
            return "用户名或者密码错误";
        String result="header:ok!\n"+list.get(0).getUid()+"&"+list.get(0).getNickname();
        return result;
    }

}
