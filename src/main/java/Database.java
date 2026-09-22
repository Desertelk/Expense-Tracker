import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;

public class Database {
        private final String url = "jdbc:postgresql://localhost:5432/expense_tracker";
        private final String username = "postgres";
        private final String password = System.getenv("DB_PASSWORD");

    public Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e){
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return null;
        }
        
    }

    public void createTables() {
        String createExpensesTable = """
                        CREATE TABLE IF NOT EXISTS expenses (
                            id SERIAL PRIMARY KEY,
                            description VARCHAR(255) NOT NULL,
                            category VARCHAR(100) NOT NULL,
                            amount NUMERIC(10, 2) NOT NULL,
                            expense_date DATE NOT NULL
                        );
                        """;
        Connection connection = connect();
        if (connection == null) {
            return;
        }
        
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createExpensesTable);
            System.out.println("Table created successfully!");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addExpense(String description, String category, double amount, LocalDate date) {
        Connection connection = connect();

        if (connection == null) {
            return;
        }

        String insertExpense = """
                    INSERT INTO expenses (description, category, amount, expense_date)
                    VALUES (?, ?, ?, ?);
                    """;

        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(insertExpense);){
                preparedStatement.setString(1, description);
                preparedStatement.setString(2, category);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setObject(4, date);
    
                preparedStatement.executeUpdate();
                System.out.println("Expense added successfully!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getExpenses() {
        Connection connection = connect();

        if (connection == null) {
            return;
        }

        try (connection; Statement statement = connection.createStatement()){

            String retrieveAllExpenses = """
                    SELECT * FROM expenses;
                    """;
            ResultSet resultSet = statement.executeQuery(retrieveAllExpenses);
    
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category");
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    LocalDate date = resultSet.getObject("expense_date", LocalDate.class);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExpenses() {

    }

    public void deleteExpenses() {

    }
}
