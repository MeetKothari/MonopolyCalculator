import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SetupPanel extends JPanel {
    private final PlayerManager playerManager;
    private final Runnable onStartGame;
    private final JTextField nameField;
    private final JPanel playerListPanel;
    private final JButton startButton;
    private final JLabel statusLabel;

    public SetupPanel(PlayerManager playerManager, Runnable onStartGame) {
        this.playerManager = playerManager;
        this.onStartGame = onStartGame;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(30, 50, 30, 50));

        // Title
        JLabel title = new JLabel("MONOPOLY CALCULATOR", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setBorder(new EmptyBorder(10, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Input row
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JLabel nameLabel = new JLabel("Player name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameField = new JTextField(20);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JButton addButton = new JButton("Add Player");
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        inputRow.add(nameLabel);
        inputRow.add(nameField);
        inputRow.add(addButton);
        centerPanel.add(inputRow);
        centerPanel.add(Box.createVerticalStrut(10));

        // Player list
        JLabel listLabel = new JLabel("  Players:");
        listLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        listLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(listLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        playerListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(playerListPanel);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(scrollPane);

        centerPanel.add(Box.createVerticalStrut(10));

        statusLabel = new JLabel("Add 2-8 players to start", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(statusLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Start button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        startButton.setEnabled(false);
        startButton.setPreferredSize(new Dimension(250, 50));
        bottomPanel.add(startButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        Runnable addAction = () -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a player name.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (Player p : playerManager.getPlayers()) {
                if (p.getName().equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(this, "A player with that name already exists.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (playerManager.getPlayers().size() >= 8) {
                JOptionPane.showMessageDialog(this, "Maximum 8 players allowed.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            playerManager.addPlayer(name);
            nameField.setText("");
            nameField.requestFocusInWindow();
            refreshPlayerList();
        };

        addButton.addActionListener(e -> addAction.run());
        nameField.addActionListener(e -> addAction.run());
        startButton.addActionListener(e -> onStartGame.run());
    }

    private void refreshPlayerList() {
        playerListPanel.removeAll();
        List<Player> players = playerManager.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBorder(new EmptyBorder(5, 10, 5, 10));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(i % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);

            JLabel nameLabel = new JLabel((i + 1) + ".  " + p.getName());
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            row.add(nameLabel, BorderLayout.CENTER);

            JButton removeBtn = new JButton("Remove");
            removeBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
            removeBtn.addActionListener(e -> {
                playerManager.removePlayer(p);
                refreshPlayerList();
            });
            row.add(removeBtn, BorderLayout.EAST);

            playerListPanel.add(row);
        }

        int count = players.size();
        statusLabel.setText(count + " player" + (count != 1 ? "s" : "") + " added  |  2-8 players required");
        startButton.setEnabled(count >= 2);

        playerListPanel.revalidate();
        playerListPanel.repaint();
    }

    public void refresh() {
        refreshPlayerList();
    }
}
