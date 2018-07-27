import java.util.LinkedList;

public class Producer {

    private LinkedList<Integer> list;
    private Integer limit = 10;
    private final Object lock;

    public Producer(LinkedList<Integer> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    public void produce() {
        int value = 0;

        while (true) {
            synchronized (lock) {
                while (list.size() == limit){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(value++);
                lock.notify();
            }
        }
    }
}
