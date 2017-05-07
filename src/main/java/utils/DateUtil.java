package utils;

import java.time.LocalDateTime;

/**
 * Created by pro on 17/5/5.
 */
public class DateUtil {

    public static String getNowDate(){
        LocalDateTime d=LocalDateTime.now();
        return d.getYear()+"-"+d.getMonth()+"-"+d.getDayOfMonth()+"- "
                +d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }
}
