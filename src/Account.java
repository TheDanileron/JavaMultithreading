public class Account {

    public int balance = 1000;

    public void deposit(int amount) {
        balance+= amount;
    }

    public void withdraw(int amount) {
        balance-=amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account accFrom, Account accTo, int amount) {
        accFrom.withdraw(amount);
        accTo.deposit(amount);
    }
}
