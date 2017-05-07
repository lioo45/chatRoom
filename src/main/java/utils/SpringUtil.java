package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pro on 17/5/6.
 */
public class SpringUtil {


    public static ApplicationContext context=
            new ClassPathXmlApplicationContext("spring/*.xml");

    public static  Object getBean(Class clazz){
      return  context.getBean(clazz);
    }
}
