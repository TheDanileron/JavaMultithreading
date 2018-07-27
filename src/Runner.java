import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private Account accFirst = new Account();
    private Account accSecond = new Account();

    private Lock lockAccFirst = new ReentrantLock();
    private Lock lockAccSecond = new ReentrantLock();

    private void acquireLocks(Lock lockFirst, Lock lockSecond) {

        while (true) {
            boolean firstLockAcquired = false;
            boolean secondLockAcquired = false;

            try {
                firstLockAcquired = lockFirst.tryLock();
                secondLockAcquired = lockSecond.tryLock();
            } finally {
                if(firstLockAcquired && secondLockAcquired) {
                    return;
                }

                if(firstLockAcquired) {
                   lockFirst.unlock();
                }

                if(secondLockAcquired) {
                    lockSecond.unlock();
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void firstThreadMethod() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            // The first thread gains a lock on lockAccFirst
            // And in the same time second thread gains lock on a lockAccSecond
            // and this leads us to a DEADLOCK when first thread waits for the lockAccSeconds to unclock and
            // the second thread waits for lockAccFirst to unlock and they just wait forever
//            lockAccFirst.lock();
//            lockAccSecond.lock();

            acquireLocks(lockAccFirst, lockAccSecond);
            try {
                Account.transfer(accFirst, accSecond, random.nextInt(accFirst.getBalance()));
            } finally {
                lockAccFirst.unlock();
                lockAccSecond.unlock();
            }
        }
    }

    public void secondThreadMethod() {
        Random random = new Random();
        for (int i = 0; i < 90; i++) {
//            lockAccSecond.lock();
//            lockAccFirst.lock();

            acquireLocks(lockAccSecond, lockAccFirst);
            try {
                Account.transfer(accSecond, accFirst, random.nextInt(accSecond.getBalance()));
            } finally {
                lockAccFirst.unlock();
                lockAccSecond.unlock();
            }
        }
    }

    public void finish() {
        System.out.println("AccountFirst balance : " + accFirst.getBalance());
        System.out.println("AccountSecond balance : " + accSecond.getBalance());
        System.out.println("Total balance : " + (accFirst.getBalance() + accSecond.getBalance()));
    }
}
