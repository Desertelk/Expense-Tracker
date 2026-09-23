import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.util.ArrayList;

class Main {
    public static void main(String[] args){

        Database database = new Database();
        Connection connection = database.connect();
        database.createTables();
        // database.addExpense("Gasoline", "Need", 12.00, LocalDate.now());
        ArrayList<Expense> expenses = database.getExpenses();
        for (Expense expense : expenses){
            System.out.printf("%d | %s | %s | %.2f | %s%n",
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getDate()
            );
        }
        

            // 
            // ResultSet resultSet = statement.executeQuery(createTable);

            // while (resultSet.next()) {
            //     int id = resultSet.getInt("id");
            //     String description = resultSet.getString("description");
            //     String category = resultSet.getString("category");
            //     Float amount = resultSet.getFloat("amount");
            //     LocalDate date = resultSet.getObject("expense_date", LocalDate.class);
            // }

            

            // 

            // 

            // String description = "Groceries";
            // String category = "Food";
            // double amount = 54.23;
            // LocalDate date = LocalDate.now();

            // 
            // connection.close();
        } 
        }

