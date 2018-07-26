import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Process process = new Process();
        process.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        process.shutDown();
    }
}

class Process extends Thread{
    // Each thread caches the values of variables from the memory. Thus the thread does not have to always refer the memory,
    // and it can simply read the values from the cache. This is gives rise to the problem of visibility.
    // This cache may not be consistent with what other variables see. With volatile, we skip the cache and read/write
    // directly in the memory. Thus, changes are visible to all threads.﻿
    // To make sure current Thread will see the changes made by other Thread we must skip cache using volatile
    private volatile boolean isRunning = true;

    @Override
    public void run() {

        while (isRunning) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I'm running");
        }
    }

    void shutDown() {
        this.isRunning = false;
    }
}