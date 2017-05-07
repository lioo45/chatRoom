import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pro on 17/5/6.
 */
public class Main {
    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        ApplicationContext context=
                new ClassPathXmlApplicationContext("spring/*.xml");
//                new ClassPathXmlApplicationContext("classpath:resources/spring/applicationContext-dao.xml");


    }
}
