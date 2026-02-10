```bash
Monopoly Calculator
```

A Java Swing application that replaces physical cash in Monopoly. Track player balances, transfer money between players, and use preset shortcuts for common transactions—all without touching a single paper bill.

![Java](https://img.shields.io/badge/Java-21%2B-orange)
![License](https://img.shields.io/badge/License-MIT-blue)

## Features

- **Player Management** — Add 2-8 players, each starting with $1,500
- **Custom Transactions** — Transfer any amount between players or to/from the bank
- **Preset Shortcuts** — One-click buttons for common Monopoly transactions:
  - Pass GO (+$200)
  - Income Tax (-$200), Luxury Tax (-$100)
  - Chance/Community Chest cards (dividends, fees, inheritance, etc.)
  - Property purchases (all standard prices from $60-$400)
  - House building costs ($50-$200 per tier)
  - Go to Jail marker
- **Live Balance Display** — Color-coded player cards (green for positive, red for negative)
- **Transaction History** — Timestamped log of all transactions
- **Undo** — Reverse the last transaction if you make a mistake
- **House Rules Notes** — Text area to record custom rules (e.g., "No rent between family members")
- **Mid-Game Player Addition** — New players can join anytime with $1,500

## Screenshots

| Setup Screen                | Game Screen                          |
| :-------------------------: | :----------------------------------: |
| Add players before starting | Track balances and make transactions |

## Getting Started

### Prerequisites

- Java 21 or higher (uses simplified `void main()` entry point)

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/yourusername/monopoly-calculator.git
cd monopoly-calculator
```

2. Compile and run:
```bash
javac -d out src/*.java
java --enable-preview -cp out Main
```

   Or open the project in IntelliJ IDEA and run `Main.java`.

## Usage

1. **Setup** — Enter player names and click "Add Player" (2-8 players required)
2. **Start Game** — Click "START GAME" to begin
3. **Make Transactions**:
   4. Use the **Custom Transaction** section for any amount
   5. Click **Quick Action** buttons for common presets
   6. Use the **Buy Property** dropdown for property purchases
4. **Track Progress** — View balances on the left, transaction history at the bottom
5. **Undo** — Click "Undo Last" to reverse a mistake
6. **New Game** — Click "New Game" to reset and start over

## Project Structure

```
src/
├── Main.java                 # Entry point
├── MonopolyCalculator.java   # Main JFrame with screen switching
├── Player.java               # Player data model
├── Transaction.java          # Transaction record
├── PresetTransaction.java    # Enum of all preset transactions
├── PlayerManager.java        # Core business logic
├── SetupPanel.java           # Player setup screen
├── GamePanel.java            # Main game screen
├── PlayerBalancePanel.java   # Player balance cards
├── TransactionPanel.java     # Transaction controls & presets
└── TransactionLogPanel.java  # Transaction history log
```

## License

This project is licensed under the MIT License.

---

Made for family game nights where nobody wants to be the banker.
