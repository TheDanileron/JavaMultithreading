public class Producer {

    private final Object lock;

    public Producer(Object lock) {
        this.lock = lock;
    }

    public void produce() {
        synchronized (lock) {
            System.out.println("Producer started ...");
            try {
                // Pauses the Thread. Releases the lock
                // To stop the thread that currently has a lock I have to call wait method of THAT lock
                // if I just call wait I'll call wait method of this, and I don't own 'this' monitor
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer resumed");
        }
    }
}
