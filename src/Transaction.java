import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transaction {
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final LocalDateTime timestamp;
    private final String fromName;
    private final String toName;
    private final int amount;
    private final String description;
    private final boolean isUndo;

    public Transaction(String fromName, String toName, int amount, String description) {
        this(fromName, toName, amount, description, false);
    }

    public Transaction(String fromName, String toName, int amount, String description, boolean isUndo) {
        this.timestamp = LocalDateTime.now();
        this.fromName = fromName;
        this.toName = toName;
        this.amount = amount;
        this.description = description;
        this.isUndo = isUndo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToName() {
        return toName;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUndo() {
        return isUndo;
    }

    @Override
    public String toString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
        fmt.setMaximumFractionDigits(0);
        String prefix = isUndo ? "[UNDO] " : "";
        if (amount == 0) {
            return String.format("[%s] %s%s -> %s (%s)",
                    timestamp.format(TIME_FMT), prefix, fromName, toName, description);
        }
        return String.format("[%s] %s%s -> %s: %s (%s)",
                timestamp.format(TIME_FMT), prefix, fromName, toName, fmt.format(amount), description);
    }
}
