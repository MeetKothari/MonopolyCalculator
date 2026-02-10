public enum PresetTransaction {
    // Income (Bank -> Player)
    PASS_GO("Pass GO", +200, "Passed GO"),
    BANK_DIVIDEND("Dividend", +50, "Bank pays dividend"),
    BUILDING_MATURES("Building Matures", +150, "Building & loan matures"),
    BEAUTY_CONTEST("Beauty Contest", +10, "Won second prize"),
    INHERITANCE("Inheritance", +100, "Collect inheritance"),
    LIFE_INSURANCE("Life Insurance", +100, "Life insurance matures"),
    CONSULTANCY_FEE("Consultancy", +25, "Receive consultancy fee"),
    TAX_REFUND("Tax Refund", +20, "Income tax refund"),

    // Expenses (Player -> Bank)
    INCOME_TAX("Income Tax", -200, "Pay income tax"),
    LUXURY_TAX("Luxury Tax", -100, "Pay luxury tax"),
    DOCTOR_FEE("Doctor Fee", -50, "Pay doctor's fee"),
    HOSPITAL_FEE("Hospital", -100, "Pay hospital fee"),
    SCHOOL_FEE("School Fee", -50, "Pay school fee"),
    STREET_REPAIRS("Repairs", -40, "Pay for street repairs"),

    // Property purchases
    BUY_PROPERTY_60("Property ($60)", -60, "Purchased property"),
    BUY_PROPERTY_100("Property ($100)", -100, "Purchased property"),
    BUY_PROPERTY_120("Property ($120)", -120, "Purchased property"),
    BUY_PROPERTY_140("Property ($140)", -140, "Purchased property"),
    BUY_PROPERTY_150("Property ($150)", -150, "Purchased property"),
    BUY_PROPERTY_160("Property ($160)", -160, "Purchased property"),
    BUY_PROPERTY_180("Property ($180)", -180, "Purchased property"),
    BUY_PROPERTY_200("Property ($200)", -200, "Purchased property"),
    BUY_PROPERTY_220("Property ($220)", -220, "Purchased property"),
    BUY_PROPERTY_240("Property ($240)", -240, "Purchased property"),
    BUY_PROPERTY_260("Property ($260)", -260, "Purchased property"),
    BUY_PROPERTY_280("Property ($280)", -280, "Purchased property"),
    BUY_PROPERTY_300("Property ($300)", -300, "Purchased property"),
    BUY_PROPERTY_320("Property ($320)", -320, "Purchased property"),
    BUY_PROPERTY_350("Property ($350)", -350, "Purchased property"),
    BUY_PROPERTY_400("Property ($400)", -400, "Purchased property"),
    BUY_RAILROAD("Railroad ($200)", -200, "Purchased railroad"),
    BUY_UTILITY("Utility ($150)", -150, "Purchased utility"),

    // Houses
    BUY_HOUSE_50("House ($50)", -50, "Built house"),
    BUY_HOUSE_100("House ($100)", -100, "Built house"),
    BUY_HOUSE_150("House ($150)", -150, "Built house"),
    BUY_HOUSE_200("House ($200)", -200, "Built house"),

    // Special
    GO_TO_JAIL("Go to Jail", 0, "Sent to Jail");

    private final String label;
    private final int amount;
    private final String description;

    PresetTransaction(String label, int amount, String description) {
        this.label = label;
        this.amount = amount;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPayment() {
        return amount < 0;
    }

    public boolean isIncome() {
        return amount > 0;
    }

    public boolean isSpecial() {
        return amount == 0;
    }
}
