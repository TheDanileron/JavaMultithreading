import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;

public class Connection {
    private static Connection instance = new Connection();
    private Semaphore semaphore = new Semaphore(10, true);
    private int connectionsCount = 0;

    private Connection() {
    }

    public void tryConnect() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            connect();
        }catch (Exception e) {
            semaphore.release();
        }
    }
    public void connect() {

        synchronized (this) {
            System.out.println("Got new connection. Connection count : " + ++connectionsCount);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            System.out.println("Connection shutdown. Connection count : " + --connectionsCount);
        }
        semaphore.release();
    }

    public static Connection getInstance() {
        return instance;
    }
}
