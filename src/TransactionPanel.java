import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class TransactionPanel extends JPanel {
    private final PlayerManager playerManager;

    private final JRadioButton playerToPlayer;
    private final JRadioButton playerToBank;
    private final JRadioButton bankToPlayer;
    private final JComboBox<String> fromDropdown;
    private final JComboBox<String> toDropdown;
    private final JTextField amountField;
    private final JTextField descriptionField;
    private final JTextArea notesArea;

    public TransactionPanel(PlayerManager playerManager) {
        this.playerManager = playerManager;

        setLayout(new BorderLayout(5, 5));
        setBorder(new EmptyBorder(5, 10, 5, 10));

        // Scrollable content so everything fits
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // === MANUAL TRANSACTION SECTION ===
        JPanel manualSection = new JPanel();
        manualSection.setLayout(new BoxLayout(manualSection, BoxLayout.Y_AXIS));
        manualSection.setBorder(new TitledBorder("Custom Transaction"));

        // Transaction type radio buttons
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        playerToPlayer = new JRadioButton("Player \u2192 Player", true);
        playerToBank = new JRadioButton("Player \u2192 Bank");
        bankToPlayer = new JRadioButton("Bank \u2192 Player");
        playerToPlayer.setFont(new Font("SansSerif", Font.PLAIN, 13));
        playerToBank.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bankToPlayer.setFont(new Font("SansSerif", Font.PLAIN, 13));

        ButtonGroup group = new ButtonGroup();
        group.add(playerToPlayer);
        group.add(playerToBank);
        group.add(bankToPlayer);
        radioPanel.add(playerToPlayer);
        radioPanel.add(playerToBank);
        radioPanel.add(bankToPlayer);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        manualSection.add(radioPanel);

        // From / To / Amount / Description
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;

        fromDropdown = new JComboBox<>();
        toDropdown = new JComboBox<>();
        amountField = new JTextField(10);
        descriptionField = new JTextField(15);

        fromDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));
        toDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        fieldsPanel.add(fromDropdown, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("  To:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        fieldsPanel.add(toDropdown, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Amount: $"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        fieldsPanel.add(amountField, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("  Note:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        fieldsPanel.add(descriptionField, gbc);

        manualSection.add(fieldsPanel);

        // Execute button
        JPanel execPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton executeBtn = new JButton("EXECUTE TRANSACTION");
        executeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        executeBtn.setPreferredSize(new Dimension(250, 35));
        execPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        execPanel.add(executeBtn);
        manualSection.add(execPanel);

        content.add(manualSection);
        content.add(Box.createVerticalStrut(5));

        // === QUICK ACTIONS (Presets) ===
        JPanel presetsSection = new JPanel();
        presetsSection.setLayout(new BoxLayout(presetsSection, BoxLayout.Y_AXIS));
        presetsSection.setBorder(new TitledBorder("Quick Actions (select a player when prompted)"));

        // Income row
        JPanel incomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        incomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel incLabel = new JLabel("Income: ");
        incLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        incLabel.setForeground(new Color(0, 120, 0));
        incomePanel.add(incLabel);
        for (PresetTransaction pt : new PresetTransaction[]{
                PresetTransaction.PASS_GO, PresetTransaction.BANK_DIVIDEND,
                PresetTransaction.BUILDING_MATURES, PresetTransaction.BEAUTY_CONTEST,
                PresetTransaction.INHERITANCE, PresetTransaction.LIFE_INSURANCE,
                PresetTransaction.CONSULTANCY_FEE, PresetTransaction.TAX_REFUND
        }) {
            JButton btn = makePresetButton(pt);
            btn.setForeground(new Color(0, 100, 0));
            incomePanel.add(btn);
        }
        presetsSection.add(incomePanel);

        // Expense row
        JPanel expensePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        expensePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel expLabel = new JLabel("Expenses: ");
        expLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        expLabel.setForeground(new Color(180, 0, 0));
        expensePanel.add(expLabel);
        for (PresetTransaction pt : new PresetTransaction[]{
                PresetTransaction.INCOME_TAX, PresetTransaction.LUXURY_TAX,
                PresetTransaction.DOCTOR_FEE, PresetTransaction.HOSPITAL_FEE,
                PresetTransaction.SCHOOL_FEE, PresetTransaction.STREET_REPAIRS,
                PresetTransaction.GO_TO_JAIL
        }) {
            JButton btn = makePresetButton(pt);
            btn.setForeground(new Color(160, 0, 0));
            incomePanel.add(btn);
            expensePanel.add(btn);
        }
        presetsSection.add(expensePanel);

        // Property purchase row
        JPanel propPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        propPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel propLabel = new JLabel("Buy Property: ");
        propLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        propPanel.add(propLabel);

        JComboBox<PresetTransaction> propertyDropdown = new JComboBox<>();
        propertyDropdown.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (PresetTransaction pt : PresetTransaction.values()) {
            if (pt.name().startsWith("BUY_PROPERTY_") || pt == PresetTransaction.BUY_RAILROAD || pt == PresetTransaction.BUY_UTILITY) {
                propertyDropdown.addItem(pt);
            }
        }
        propertyDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean sel, boolean focus) {
                super.getListCellRendererComponent(list, value, index, sel, focus);
                if (value instanceof PresetTransaction pt) {
                    setText(pt.getLabel());
                }
                return this;
            }
        });
        propPanel.add(propertyDropdown);

        JButton buyPropBtn = new JButton("Buy");
        buyPropBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        buyPropBtn.addActionListener(e -> {
            PresetTransaction selected = (PresetTransaction) propertyDropdown.getSelectedItem();
            if (selected != null) {
                Player target = askForPlayer("Who is buying " + selected.getLabel() + "?");
                if (target != null) {
                    playerManager.applyPreset(selected, target);
                }
            }
        });
        propPanel.add(buyPropBtn);
        presetsSection.add(propPanel);

        // House row
        JPanel housePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        housePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel houseLabel = new JLabel("Build House: ");
        houseLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        housePanel.add(houseLabel);
        for (PresetTransaction pt : new PresetTransaction[]{
                PresetTransaction.BUY_HOUSE_50, PresetTransaction.BUY_HOUSE_100,
                PresetTransaction.BUY_HOUSE_150, PresetTransaction.BUY_HOUSE_200
        }) {
            JButton btn = makePresetButton(pt);
            housePanel.add(btn);
        }
        presetsSection.add(housePanel);

        content.add(presetsSection);
        content.add(Box.createVerticalStrut(5));

        // === HOUSE RULES / NOTES ===
        JPanel notesSection = new JPanel(new BorderLayout(5, 5));
        notesSection.setBorder(new TitledBorder("House Rules & Notes"));
        notesArea = new JTextArea(4, 20);
        notesArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setText("Write any house rules here (e.g., \"Alice doesn't charge Bob rent on Boardwalk\")...");
        notesArea.setForeground(Color.GRAY);
        notesArea.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (notesArea.getForeground() == Color.GRAY) {
                    notesArea.setText("");
                    notesArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (notesArea.getText().trim().isEmpty()) {
                    notesArea.setText("Write any house rules here (e.g., \"Alice doesn't charge Bob rent on Boardwalk\")...");
                    notesArea.setForeground(Color.GRAY);
                }
            }
        });
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesSection.add(notesScroll, BorderLayout.CENTER);
        content.add(notesSection);

        // Wrap content in scroll pane
        JScrollPane mainScroll = new JScrollPane(content);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);

        // Radio button listeners to update dropdowns
        playerToPlayer.addActionListener(e -> updateDropdowns());
        playerToBank.addActionListener(e -> updateDropdowns());
        bankToPlayer.addActionListener(e -> updateDropdowns());

        // Execute button action
        executeBtn.addActionListener(e -> executeTransaction());

        refreshPlayerDropdowns();
    }

    private JButton makePresetButton(PresetTransaction pt) {
        String text = pt.getLabel();
        if (pt.getAmount() != 0) {
            text += (pt.getAmount() > 0 ? " +$" : " -$") + Math.abs(pt.getAmount());
        }
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        btn.setMargin(new Insets(2, 5, 2, 5));
        btn.addActionListener(e -> {
            Player target = askForPlayer(pt.getLabel() + " - Select player:");
            if (target != null) {
                playerManager.applyPreset(pt, target);
            }
        });
        return btn;
    }

    private Player askForPlayer(String message) {
        List<Player> players = playerManager.getPlayers();
        if (players.isEmpty()) return null;

        String[] names = players.stream().map(Player::getName).toArray(String[]::new);
        String chosen = (String) JOptionPane.showInputDialog(
                this, message, "Select Player",
                JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

        if (chosen == null) return null;
        for (Player p : players) {
            if (p.getName().equals(chosen)) return p;
        }
        return null;
    }

    private void executeTransaction() {
        String amountText = amountField.getText().replaceAll("[^0-9]", "");
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Amount must be greater than 0.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String desc = descriptionField.getText().trim();
        if (desc.isEmpty()) desc = "Custom transaction";

        boolean success = false;
        if (playerToPlayer.isSelected()) {
            Player from = getSelectedPlayer(fromDropdown);
            Player to = getSelectedPlayer(toDropdown);
            if (from == null || to == null) {
                JOptionPane.showMessageDialog(this, "Select both players.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (from == to) {
                JOptionPane.showMessageDialog(this, "Cannot transfer to the same player.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            success = playerManager.transferBetweenPlayers(from, to, amount, desc);
        } else if (playerToBank.isSelected()) {
            Player from = getSelectedPlayer(fromDropdown);
            if (from == null) {
                JOptionPane.showMessageDialog(this, "Select a player.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            success = playerManager.payBank(from, amount, desc);
        } else if (bankToPlayer.isSelected()) {
            Player to = getSelectedPlayer(toDropdown);
            if (to == null) {
                JOptionPane.showMessageDialog(this, "Select a player.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            success = playerManager.receiveFromBank(to, amount, desc);
        }

        if (success) {
            amountField.setText("");
            descriptionField.setText("");
        }
    }

    private Player getSelectedPlayer(JComboBox<String> dropdown) {
        String name = (String) dropdown.getSelectedItem();
        if (name == null || name.equals("Bank")) return null;
        for (Player p : playerManager.getPlayers()) {
            if (p.getName().equals(name)) return p;
        }
        return null;
    }

    public void refreshPlayerDropdowns() {
        updateDropdowns();
    }

    private void updateDropdowns() {
        String prevFrom = (String) fromDropdown.getSelectedItem();
        String prevTo = (String) toDropdown.getSelectedItem();

        fromDropdown.removeAllItems();
        toDropdown.removeAllItems();

        List<Player> players = playerManager.getPlayers();

        if (playerToPlayer.isSelected()) {
            for (Player p : players) {
                fromDropdown.addItem(p.getName());
                toDropdown.addItem(p.getName());
            }
            fromDropdown.setEnabled(true);
            toDropdown.setEnabled(true);
        } else if (playerToBank.isSelected()) {
            for (Player p : players) {
                fromDropdown.addItem(p.getName());
            }
            toDropdown.addItem("Bank");
            fromDropdown.setEnabled(true);
            toDropdown.setEnabled(false);
        } else if (bankToPlayer.isSelected()) {
            fromDropdown.addItem("Bank");
            for (Player p : players) {
                toDropdown.addItem(p.getName());
            }
            fromDropdown.setEnabled(false);
            toDropdown.setEnabled(true);
        }

        // Restore previous selections if still valid
        if (prevFrom != null) fromDropdown.setSelectedItem(prevFrom);
        if (prevTo != null) toDropdown.setSelectedItem(prevTo);
    }
}
