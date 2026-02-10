import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerManager {
    private final List<Player> players = new ArrayList<>();
    private final List<Transaction> history = new ArrayList<>();
    private GameStateListener listener;

    public interface GameStateListener {
        void onPlayerBalanceChanged();
        void onTransactionLogged(Transaction t);
        void onPlayerListChanged();
    }

    public void setListener(GameStateListener listener) {
        this.listener = listener;
    }

    public Player addPlayer(String name) {
        Player p = new Player(name);
        players.add(p);
        if (listener != null) listener.onPlayerListChanged();
        return p;
    }

    public void removePlayer(Player p) {
        players.remove(p);
        if (listener != null) listener.onPlayerListChanged();
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public boolean transferBetweenPlayers(Player from, Player to, int amount, String description) {
        if (amount <= 0 || from == to) return false;
        from.adjustBalance(-amount);
        to.adjustBalance(amount);
        Transaction t = new Transaction(from.getName(), to.getName(), amount, description);
        history.add(t);
        notifyChange(t);
        return true;
    }

    public boolean payBank(Player from, int amount, String description) {
        if (amount <= 0) return false;
        from.adjustBalance(-amount);
        Transaction t = new Transaction(from.getName(), "Bank", amount, description);
        history.add(t);
        notifyChange(t);
        return true;
    }

    public boolean receiveFromBank(Player to, int amount, String description) {
        if (amount <= 0) return false;
        to.adjustBalance(amount);
        Transaction t = new Transaction("Bank", to.getName(), amount, description);
        history.add(t);
        notifyChange(t);
        return true;
    }

    public boolean customAddToPlayer(Player player, int amount, String description) {
        if (amount <= 0) return false;
        player.adjustBalance(amount);
        Transaction t = new Transaction("Bank", player.getName(), amount, description);
        history.add(t);
        notifyChange(t);
        return true;
    }

    public boolean customDeductFromPlayer(Player player, int amount, String description) {
        if (amount <= 0) return false;
        player.adjustBalance(-amount);
        Transaction t = new Transaction(player.getName(), "Bank", amount, description);
        history.add(t);
        notifyChange(t);
        return true;
    }

    public boolean applyPreset(PresetTransaction preset, Player target) {
        if (preset == PresetTransaction.GO_TO_JAIL) {
            target.setInJail(true);
            Transaction t = new Transaction(target.getName(), "Jail", 0, preset.getDescription());
            history.add(t);
            notifyChange(t);
            return true;
        }
        if (preset.isIncome()) {
            return receiveFromBank(target, preset.getAmount(), preset.getDescription());
        } else if (preset.isPayment()) {
            return payBank(target, Math.abs(preset.getAmount()), preset.getDescription());
        }
        return false;
    }

    public boolean undoLast() {
        if (history.isEmpty()) return false;
        Transaction last = history.get(history.size() - 1);
        if (last.isUndo()) return false;

        // Reverse the transaction
        if (last.getAmount() == 0) {
            // Special transaction (jail)
            Player target = findPlayer(last.getFromName());
            if (target != null) {
                target.setInJail(false);
                Transaction undo = new Transaction(last.getFromName(), "Jail", 0, "Undo: " + last.getDescription(), true);
                history.add(undo);
                notifyChange(undo);
            }
            return true;
        }

        String from = last.getFromName();
        String to = last.getToName();
        int amount = last.getAmount();

        Player fromPlayer = findPlayer(from);
        Player toPlayer = findPlayer(to);

        if ("Bank".equals(from) && toPlayer != null) {
            toPlayer.adjustBalance(-amount);
        } else if ("Bank".equals(to) && fromPlayer != null) {
            fromPlayer.adjustBalance(amount);
        } else if (fromPlayer != null && toPlayer != null) {
            fromPlayer.adjustBalance(amount);
            toPlayer.adjustBalance(-amount);
        } else {
            return false;
        }

        Transaction undo = new Transaction(to, from, amount, "Undo: " + last.getDescription(), true);
        history.add(undo);
        notifyChange(undo);
        return true;
    }

    private Player findPlayer(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) return p;
        }
        return null;
    }

    public void reset() {
        players.clear();
        history.clear();
        if (listener != null) listener.onPlayerListChanged();
    }

    private void notifyChange(Transaction t) {
        if (listener != null) {
            listener.onPlayerBalanceChanged();
            listener.onTransactionLogged(t);
        }
    }
}
