public class Main {

    public static void main(String[] args) {
        Runner runner = new Runner(1);
        runner.start();

        Runner runner1 = new Runner(2);
        runner1.start();
    }
}

class Runner extends Thread {

    public int runnerNumber;

    public Runner(int runnerNumber) {
        this.runnerNumber = runnerNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runner " + this.runnerNumber + " runs for " + i + " seconds");
        }
    }
}