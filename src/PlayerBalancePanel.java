import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PlayerBalancePanel extends JPanel {
    private static final Color[] PLAYER_COLORS = {
            new Color(255, 230, 230),
            new Color(230, 230, 255),
            new Color(230, 255, 230),
            new Color(255, 255, 210),
            new Color(255, 220, 255),
            new Color(220, 255, 255),
            new Color(255, 240, 220),
            new Color(240, 230, 255),
    };

    private final PlayerManager playerManager;
    private final JPanel cardsPanel;

    public PlayerBalancePanel(PlayerManager playerManager) {
        this.playerManager = playerManager;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(230, 0));

        JLabel header = new JLabel("PLAYERS", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBorder(new EmptyBorder(5, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        cardsPanel.removeAll();
        List<Player> players = playerManager.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            JPanel card = createPlayerCard(p, i);
            cardsPanel.add(card);
            cardsPanel.add(Box.createVerticalStrut(5));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createPlayerCard(Player player, int index) {
        Color bgColor = PLAYER_COLORS[index % PLAYER_COLORS.length];

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(new CompoundBorder(
                new LineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Name
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(nameLabel);

        // Balance
        JLabel balanceLabel = new JLabel(player.getFormattedBalance());
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (player.getBalance() < 0) {
            balanceLabel.setForeground(new Color(180, 0, 0));
        } else {
            balanceLabel.setForeground(new Color(0, 120, 0));
        }
        card.add(balanceLabel);

        // Jail indicator
        if (player.isInJail()) {
            JLabel jailLabel = new JLabel("IN JAIL");
            jailLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            jailLabel.setForeground(new Color(200, 0, 0));
            jailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(jailLabel);
        }

        // Buttons row
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        btnRow.setBackground(bgColor);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton goBtn = new JButton("GO +$200");
        goBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        goBtn.setMargin(new Insets(2, 4, 2, 4));
        goBtn.addActionListener(e -> {
            playerManager.applyPreset(PresetTransaction.PASS_GO, player);
        });
        btnRow.add(goBtn);

        JButton jailBtn = new JButton(player.isInJail() ? "Free" : "Jail");
        jailBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        jailBtn.setMargin(new Insets(2, 4, 2, 4));
        jailBtn.addActionListener(e -> {
            player.setInJail(!player.isInJail());
            refresh();
        });
        btnRow.add(jailBtn);

        card.add(btnRow);

        return card;
    }
}
