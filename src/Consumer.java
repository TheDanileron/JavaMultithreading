import java.util.LinkedList;
import java.util.Random;

public class Consumer {

    private LinkedList<Integer> list;
    private final Object lock;

    public Consumer(LinkedList<Integer> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    public void consume() {
        Random random = new Random();
        while (true) {
            synchronized (lock) {

                while (list.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("List size is:" + list.size());
                int value = list.removeLast();
                System.out.println("; value is:" + value);
                lock.notify();
            }

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
