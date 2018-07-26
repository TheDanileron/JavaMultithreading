import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        // CountDownLatch is one of the MANY pretty cool ThreadSafe classes and it's...
        // counting down threadSafely - ooh yeah
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Processor(latch, i));
        }

        try {
            // Current thread waits until countdown latch is counted to 0
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
        executorService.shutdown();
    }
}

class Processor implements Runnable {

    private CountDownLatch latch;
    private Integer id;
    public Processor(CountDownLatch latch, Integer id) {
        this.latch = latch;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting " + id);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("Stopping " + id);
    }
}

