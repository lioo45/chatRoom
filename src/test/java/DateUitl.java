import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by pro on 17/5/5.
 */
public class DateUitl {

    public static String getNowDate(){
        LocalDateTime d=LocalDateTime.now();
        System.out.println(d.toString());
//        return d.getYear()+"-"+d.getMonth()+"-"+d.getDayOfMonth()+"- "
        return null;
//                +d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }

    public static void main(String[] args) {
        LocalDateTime d=LocalDateTime.now();
        System.out.println(d.toString());
        String s=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss"));
        System.out.println(s);
    }
}
