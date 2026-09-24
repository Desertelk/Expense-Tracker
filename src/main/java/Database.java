import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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

    public void addExpense(String description, String category, BigDecimal amount, LocalDate date) {
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
                preparedStatement.setBigDecimal(3, amount);
                preparedStatement.setObject(4, date);
    
                preparedStatement.executeUpdate();
                System.out.println("Expense added successfully!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Expense> getExpenses() {
        Connection connection = connect();
        ArrayList<Expense> expenses = new ArrayList<>();

        if (connection == null) {
            return null;
        }

        String retrieveAllExpenses = """
                SELECT * FROM expenses
                ORDER BY id;
                """;
        try (connection; Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(retrieveAllExpenses)){

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category");
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    LocalDate date = resultSet.getObject("expense_date", LocalDate.class);
                    
                    Expense expense = new Expense(id, description, category, amount, date);

                    expenses.add(expense);
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    public void updateExpense(int id, String description, String category, BigDecimal amount, LocalDate date) {
        String updateExpense = """
                UPDATE expenses
                SET description = ?,
                    category = ?,
                    amount = ?,
                    expense_date = ?
                WHERE id = ?;
                """;

        Connection connection = connect();

        if (connection == null) {
            return;
        }

        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(updateExpense)) {
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, category);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setObject(4, date);
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("Expense updated successfully.");
            } else {
                System.out.println("No expense found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteExpense(int id) {
        String deleteExpense = """
                DELETE FROM expenses
                WHERE id = ?
                """;

        Connection connection = connect();

        if (connection == null) {
            return;
        }

        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(deleteExpense)) {
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Expense deleted successfully.");
            } else {
                System.out.println("No expense found with ID " + id);
    }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ExpenseSummary getExpenseSummary() {
        Connection connection = connect();
        String summaryQuery = """
            SELECT
                SUM(amount) AS total,
                AVG(amount) AS average
            FROM expenses;
            """;

        if (connection == null) {
            return new ExpenseSummary(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        try (connection; Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(summaryQuery)) {
            if (resultSet.next()){
                BigDecimal total = resultSet.getBigDecimal("total");
                BigDecimal average = resultSet.getBigDecimal("average");

                ExpenseSummary expenseSummary = new ExpenseSummary(total, average);

                if(total != null) {
                    return expenseSummary;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ExpenseSummary(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
