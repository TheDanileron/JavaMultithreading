import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

public class App {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting ...");
        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        Counter counter = new Counter();
        long start = System.nanoTime();

        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            final int j = i;

            futures.add(CompletableFuture.supplyAsync(
                    () -> counter.count(j),
                    threadPool
            ));
        }

        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }

        System.out.println(format("Executed by %d s, value : %f",
                (System.nanoTime() - start) / (1000_000_000),
                value));
        threadPool.shutdown();
    }

}


