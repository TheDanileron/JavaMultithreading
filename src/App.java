import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    public static void main(String[] args) {
        // Semaphore maintains a count of available permits
        Semaphore semaphore = new Semaphore(1, true);

        // Increments the number of available permits
        semaphore.release();

        try {
            // Decrements the number of available permits
            // if semaphore has no permits the require will pause current thread until some permit is available
            semaphore.acquire();
            semaphore.acquire();
//            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Semaphore available permits : " + semaphore.availablePermits());

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().tryConnect();
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


