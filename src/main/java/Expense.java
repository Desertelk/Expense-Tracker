import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {
    private int id;
    private String description;
    private String category;
    private BigDecimal amount;
    private LocalDate date;

    public Expense(int id, String description, String category, BigDecimal amount, LocalDate date){
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public String getCategory(){
        return category;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public LocalDate getDate(){
        return date;
    }
}
