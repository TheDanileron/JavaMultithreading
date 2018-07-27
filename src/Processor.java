public class Processor {

    public void produce() {

    }

    public void consume() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
