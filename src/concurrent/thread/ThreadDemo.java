package concurrent.thread;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/21 16:38
 */
public class ThreadDemo {


    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            Thread thread1 = Thread.currentThread();
            while (!thread1.isInterrupted()) {
                try {
                    System.out.println(LocalTime.now());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setName("demo-1");
        thread.start();
        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
