import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class MonsterHunt implements Callable<String> {
    private Hunter hunter;
    private Monster monster;
    private BlockingQueue<Monster> monsters;
    private BlockingQueue<Hunter> hunters;

    public MonsterHunt(BlockingQueue<Monster> monsters, BlockingQueue<Hunter> hunters) {
        try {
            this.hunter = hunters.take();
            this.monster = monsters.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.monsters = monsters;
        this.hunters = hunters;
    }

    @Override
    public String call() throws Exception {
        Random random = new Random();
        int huntTime = random.nextInt(2000);
        Thread.sleep(huntTime);
        return hunt();
    }

    public String hunt() throws InterruptedException {
        double successChance = hunter.getPower() / monster.getPower();
        Random random = new Random();

        double result = random.nextDouble();
        if (result <= successChance) {
            hunters.put(hunter);
            monsters.put(monster);
            return "Hunter " + hunter.getName() + " defeated " + monster.getName();
        } else {
            hunters.put(hunter);
            monsters.put(monster);
            return "Hunter " + hunter.getName() + " failed his mission. " + monster.getName() + " survived";
        }
    }
}
