import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by pro on 17/5/5.
 */
public class DateUitl {

    public static String getNowDate(){
        LocalDateTime d=LocalDateTime.now();
        return d.getYear()+"-"+d.getMonth()+"-"+d.getDayOfMonth()+"- "
                +d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }

    public static void main(String[] args) {
        String s="aaaasdgsdf";
        String s1[]=s.split("1");
        System.out.println(s1[0]);

    }
}
