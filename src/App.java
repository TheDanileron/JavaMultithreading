import javax.swing.plaf.TableHeaderUI;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        Object lock = new Object();

        Producer producer = new Producer(list, lock);
        Consumer consumer = new Consumer(list, lock);

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


