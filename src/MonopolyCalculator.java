import javax.swing.*;
import java.awt.*;

public class MonopolyCalculator extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final PlayerManager playerManager;
    private final SetupPanel setupPanel;
    private GamePanel gamePanel;

    public MonopolyCalculator() {
        super("Monopoly Calculator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        playerManager = new PlayerManager();
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        setupPanel = new SetupPanel(playerManager, this::startGame);
        contentPanel.add(setupPanel, "setup");

        setContentPane(contentPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        if (gamePanel != null) {
            contentPanel.remove(gamePanel);
        }
        gamePanel = new GamePanel(playerManager, this::returnToSetup);
        contentPanel.add(gamePanel, "game");
        cardLayout.show(contentPanel, "game");
    }

    private void returnToSetup() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Start a new game? All current progress will be lost.",
                "New Game", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            playerManager.reset();
            setupPanel.refresh();
            cardLayout.show(contentPanel, "setup");
        }
    }
}
