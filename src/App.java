import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Callable can return a value wrapped in a Future object, that has the same value type as value
        // specified in Callable. When we submit callable it returns a Future object;
        Future<Integer> result = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                System.out.println("Callable started ...");
                Thread.sleep(duration);
                System.out.println("Callable done its work. ");

                return duration;
            }
        });

        executorService.shutdown();

        try {
            // Future get method will wait until the Callable is terminated
            System.out.println("Time wasted on task : " + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            // get throws ExecutionException if Callable throws any exception
            e.printStackTrace();
        }
    }

}


