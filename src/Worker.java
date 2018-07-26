import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;


public class Worker {

    private Random random = new Random();
    private List<Integer> listFirst = new ArrayList<>();
    private List<Integer> listSecond = new ArrayList<>();

    private final Object lockFirst = new Object();
    private final Object lockSecond = new Object();

    public void main() {
        long startTime = System.currentTimeMillis();

        Thread threadFirst = new Thread(new Runnable() {
            @Override
            public void run() {
                processLists();
            }
        });

        Thread threadSecond = new Thread(new Runnable() {
            @Override
            public void run() {
                processLists();
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

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("FirstList size : " + listFirst.size() + "; SecondList size : " + listSecond.size()
                + "; executionTime: " + executionTime);
    }

    // When thread calls synchronized method it acquires Lock for ENTIRE OBJECT OR CLASS( if method is static ), and other Threads wont have access to this
    // object/class until the first Thread release the Lock
    // So to prevent this - we create a lock classes.
    private void firstListAddValue() {
        // So, we create a synchronized blocks for different objects xD
        // So, when thread enters a synchronized block it acquires a Lock for lockFirst object, which is needed to enter
        // this block of code
        synchronized (lockFirst) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listFirst.add(random.nextInt(100));
        }
    }

    private void secondListAddValue() {
        // and in here we acquire a Lock for lockSecond object
        synchronized (lockSecond) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listSecond.add(random.nextInt(100));
        }
    }

    // list.add is not Atomic operation so shit might happen
    private void processLists() {
        for (int i = 0; i < 1000; i++) {
            firstListAddValue();
            secondListAddValue();
        }
    }
}
