package utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by pro on 17/5/5.
 */
public class DateUtil {

    public static String getNowDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss"));

    }
}
