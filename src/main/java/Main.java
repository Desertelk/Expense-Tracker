import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class Main {
    public static void main(String[] args){
        run();
    }

    public static void run() {
        Database database = new Database();
        database.createTables();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n======Expense Tracker======");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. View Summary");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addExpense(database,scanner);
                    break;
                
                case "2":
                    viewExpenses(database);
                    break;

                case "3":
                    updateExpense(database, scanner);
                    break;

                case "4":
                    deleteExpense(database, scanner);
                    break;

                case "5":
                    viewSummary(database);
                    break;

                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6");
                    break;
            }
        }
    }

    public static void addExpense(Database database, Scanner scanner) {
        System.out.println("Please give us the following information: ");
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("\nCategory: ");
        String category = scanner.nextLine();

        System.out.print("\nAmount: ");
        BigDecimal amount = new BigDecimal( scanner.nextLine());

        System.out.print("Did you make this purchase today (Yes or No): ");
        String choice = scanner.nextLine();
        LocalDate date = null;
        if (choice.equalsIgnoreCase("yes")){
            date = LocalDate.now();
        } else {
            System.out.println("Enter the following information about the expense.");
            System.out.print("Year: (ex. 2026): ");
            int year = scanner.nextInt();
            System.out.print("\nMonth: (1-12): ");
            int month = scanner.nextInt();
            System.out.print("\nDay: ");
            int day = scanner.nextInt();

            date = LocalDate.of(year, month, day);
        }

        database.addExpense(description, category, amount, date);
    }

    public static void viewExpenses(Database database) {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses = database.getExpenses();
        for (Expense expense : expenses){
            System.out.printf("%d | %s | %s | %.2f | %s%n",
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getDate()
            );
        }
    }

    public static void updateExpense(Database database, Scanner scanner){
        viewExpenses(database);
        System.out.print("Please choose the ID of the expense you want to upate: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Please input the following information.");
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        System.out.println("Enter the following information about the date of the expense.");
            System.out.print("Year: (ex. 2026): ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("\nMonth: (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.print("\nDay: ");
            int day = Integer.parseInt(scanner.nextLine());

        LocalDate date = LocalDate.of(year, month, day);

        database.updateExpense(id, description, category, amount, date);
    }

    public static void deleteExpense(Database database, Scanner scanner) {
        viewExpenses(database);
        System.out.print("Please choose the ID of the expense you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        database.deleteExpense(id);
    }

    public static void viewSummary(Database database) {
        ExpenseSummary expenseSummary = database.getExpenseSummary();
        System.out.printf("%nTotal spent: %.2f%nAverage spent: %.2f%n", expenseSummary.getTotal(), expenseSummary.getAverage());
    }
}

