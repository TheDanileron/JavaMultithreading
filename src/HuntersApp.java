import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

public class HuntersApp {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting ...");

        BlockingQueue<Hunter> hunters = new LinkedBlockingQueue<>();
        hunters.add(new Hunter("Tobby", Powers.HUNTER_MEDIUM));
        hunters.add(new Hunter("Billy", Powers.HUNTER_WEAK));
        hunters.add(new Hunter("Jill", Powers.HUNTER_MEDIUM));
        hunters.add(new Hunter("Victor", Powers.HUNTER_STRONG));

        BlockingQueue<Monster> monsters = new LinkedBlockingQueue<>();
        monsters.add(new Monster("Ratiana", Powers.MONSTER_STRONG));
        monsters.add(new Monster("Aniatod", Powers.MONSTER_STRONG));
        monsters.add(new Monster("Tobi-ka-dachi", Powers.MONSTER_MEDIUM));
        monsters.add(new Monster("Kulu-ya-ku", Powers.MONSTER_WEAK));

        ExecutorService executorService = Executors.newFixedThreadPool(hunters.size());

        while (true) {
            Future<String> huntResult = executorService.submit(new MonsterHunt(monsters, hunters));
        }
    }

}


