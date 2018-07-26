import javax.swing.plaf.TableHeaderUI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {
    // First in first out
    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) {
        App app = new App();
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                app.produce();
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                app.consume();
            }
        });

        producer.start();
        consumer.start();
    }

    private void produce() {
        Random random = new Random();
        while (true) {
            try {
                queue.put(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void consume() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (random.nextInt(10) == 0) {

                Integer value = null;
                try {
                    value = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Taken value: " + value + "; Queue size is " + queue.size());

            }
        }
    }
}


