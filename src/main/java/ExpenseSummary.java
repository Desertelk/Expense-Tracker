import java.math.BigDecimal;

public class ExpenseSummary {
    private BigDecimal total;
    private BigDecimal average;

    // Constructor of the expense summary
    public ExpenseSummary (BigDecimal total, BigDecimal average) {
        this.total = total;
        this.average = average;
    }

    // Getter for the total amount
    public BigDecimal getTotal(){
        return total;
    }

    // Getter for the average of expenses
    public BigDecimal getAverage() {
        return average;
    }
}
