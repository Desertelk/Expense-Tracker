import java.math.BigDecimal;

public class ExpenseSummary {
    private BigDecimal total;
    private BigDecimal average;

    public ExpenseSummary (BigDecimal total, BigDecimal average) {
        this.total = total;
        this.average = average;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public BigDecimal getAverage() {
        return average;
    }
}
