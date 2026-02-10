import java.text.NumberFormat;
import java.util.Locale;

public class Player {
    private static int nextId = 1;

    private final int id;
    private final String name;
    private int balance;
    private boolean inJail;

    public Player(String name) {
        this.id = nextId++;
        this.name = name;
        this.balance = 1500;
        this.inJail = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void adjustBalance(int amount) {
        this.balance += amount;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public String getFormattedBalance() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
        fmt.setMaximumFractionDigits(0);
        return fmt.format(balance);
    }

    @Override
    public String toString() {
        return name + " (" + getFormattedBalance() + ")";
    }
}
