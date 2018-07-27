import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    private static Random random = new Random();
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static void addValuesFirstThread(List<Integer> list) {
        for (int i = 0; i < 10; i++) {
            // Lock is kinda like a synchronized block - when one thread calls lock.lock() method it locks the Lock
            // object and another thread gonna have to wait until first thread unlocks the Lock
            lock.lock();
            System.out.println("First thread gained the lock ... ");
            try {
                // condition.await() will release the lock for the next thread
                // it's like a wait method
                System.out.println("First thread awaits ... ");
                condition.await();
                System.out.println("First thread received the signal ... ");
                list.add(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // However if code between lock and unlock method throws an exception unlock never will be called
                // so we use try-finally
                lock.unlock();
                System.out.println("First thread released the lock ... ");
            }
        }
    }

    private static void addValuesSecondThread(List<Integer> list) {
        for (int i = 0; i < 10; i++) {
            // Lock is kinda like a synchronized block - when one thread calls lock.lock() method it locks the Lock
            // object and another thread gonna have to wait until first thread unlocks the Lock
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println("Second thread gained the lock ... ");
            try {
                // condition.await() will release the lock for the next thread
                // it's like a wait method
                System.out.println("Second thread sends the signal ... ");
                condition.signal();
                list.add(random.nextInt(100));
            } finally {
                // However if code between lock and unlock method throws an exception unlock never will be called
                // so we use try-finally
                System.out.println("Second thread unlocks the lock ... ");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();

        Thread threadFirst = new Thread(new Runnable() {
            @Override
            public void run() {
                addValuesFirstThread(list);
            }
        });

        Thread threadSecond = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addValuesSecondThread(list);
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

        System.out.println("List size : " + list.size());
    }
}


