import lwz.mapper.UserMapper;
import lwz.pojo.User;
import lwz.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.channels.Pipe;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by pro on 17/5/5.
 */
public class Test {
    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        ApplicationContext context=
                new ClassPathXmlApplicationContext("spring/*.xml");
//                new ClassPathXmlApplicationContext("classpath:resources/spring/applicationContext-dao.xml");

        UserService service=context.getBean(UserService.class);
//        User user=new User("bb","bb","bb");
////        user.setUid(10);
////        service.addUser(user);
//        UserMapper mapper=context.getBean(UserMapper.class);
//        mapper.insert(user);

        //测试 getFriends
        List<User> list=service.getFriends(1);
        System.out.println("size: "+list.size());

    }




}
