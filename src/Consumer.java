import java.util.Scanner;

public class Consumer {

    private final Object lock;

    public Consumer(Object lock) {
        this.lock = lock;
    }

    public void consume() {
        synchronized (lock) {
            System.out.println("Consumer entered the lock. Waiting for key");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            // Notify wake's up the Thread that sleeps in the same Lock ( the one that called wait method )
            // However The awaken Thread wont be able to get the lock until current thread releases it
            lock.notifyAll();
        }
    }
}
