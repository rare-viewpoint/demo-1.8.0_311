package datetime;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/6 21:51
 */
public class TimeDemo {

    public static void main(String[] args) {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println( TimeUnit.SECONDS.toNanos(localTime.getSecond()));
    }
}
