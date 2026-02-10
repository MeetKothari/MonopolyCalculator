import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GamePanel extends JPanel implements PlayerManager.GameStateListener {
    private final PlayerManager playerManager;
    private final Runnable onNewGame;
    private final PlayerBalancePanel balancePanel;
    private final TransactionPanel transactionPanel;
    private final TransactionLogPanel logPanel;

    public GamePanel(PlayerManager playerManager, Runnable onNewGame) {
        this.playerManager = playerManager;
        this.onNewGame = onNewGame;

        playerManager.setListener(this);

        setLayout(new BorderLayout(5, 5));

        // Header
        JPanel header = new JPanel(new BorderLayout(10, 0));
        header.setBorder(new EmptyBorder(8, 15, 8, 15));

        JLabel title = new JLabel("MONOPOLY CALCULATOR");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton addPlayerBtn = new JButton("+ Add Player");
        addPlayerBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        addPlayerBtn.addActionListener(e -> addPlayerMidGame());
        headerButtons.add(addPlayerBtn);

        JButton undoBtn = new JButton("Undo Last");
        undoBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        undoBtn.addActionListener(e -> {
            if (!playerManager.undoLast()) {
                JOptionPane.showMessageDialog(this, "Nothing to undo.", "Undo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        headerButtons.add(undoBtn);

        JButton newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        newGameBtn.addActionListener(e -> onNewGame.run());
        headerButtons.add(newGameBtn);

        header.add(headerButtons, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Left - Player balances
        balancePanel = new PlayerBalancePanel(playerManager);
        add(balancePanel, BorderLayout.WEST);

        // Center - Transaction controls
        transactionPanel = new TransactionPanel(playerManager);
        add(transactionPanel, BorderLayout.CENTER);

        // South - Transaction log
        logPanel = new TransactionLogPanel();
        add(logPanel, BorderLayout.SOUTH);
    }

    private void addPlayerMidGame() {
        if (playerManager.getPlayers().size() >= 8) {
            JOptionPane.showMessageDialog(this, "Maximum 8 players.", "Limit", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter new player name:", "Add Player", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        name = name.trim();
        for (Player p : playerManager.getPlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(this, "A player with that name already exists.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        playerManager.addPlayer(name);
    }

    @Override
    public void onPlayerBalanceChanged() {
        balancePanel.refresh();
    }

    @Override
    public void onTransactionLogged(Transaction t) {
        logPanel.appendTransaction(t);
    }

    @Override
    public void onPlayerListChanged() {
        balancePanel.refresh();
        transactionPanel.refreshPlayerDropdowns();
    }
}
