import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TransactionLogPanel extends JPanel {
    private final JTextArea logArea;

    public TransactionLogPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Transaction History"));
        setPreferredSize(new Dimension(0, 160));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        logArea.setLineWrap(false);
        logArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendTransaction(Transaction t) {
        logArea.append(t.toString() + "\n");
        // Auto-scroll to bottom
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void clear() {
        logArea.setText("");
    }
}
