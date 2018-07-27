import javax.swing.plaf.TableHeaderUI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) {
        Object lock = new Object();
        Producer producer = new Producer(lock);
        Consumer consumer = new Consumer(lock);

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                producer.produce();
            }
        });

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.consume();
            }
        });

        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


