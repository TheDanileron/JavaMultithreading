import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    public static void main(String[] args) {
        Runner runner = new Runner();
        Thread threadFirst = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.firstThreadMethod();
            }
        });

        Thread threadSecond = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.secondThreadMethod();
            }
        });

        threadFirst.start();
        threadSecond.start();

        try {
            threadFirst.join();
            threadSecond.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runner.finish();
    }
}


