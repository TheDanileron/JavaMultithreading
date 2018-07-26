import static java.lang.Thread.sleep;

public class MainTwo {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnerI(3));
        thread1.start();
        Thread thread2 = new Thread(new RunnerI(4));
        thread2.start();
    }
}

class RunnerI implements Runnable {

    private int runnerNumber;

    public RunnerI(int runnerNumber) {
        this.runnerNumber = runnerNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runner " + this.runnerNumber + " runs for " + i + " seconds");
        }
    }
}