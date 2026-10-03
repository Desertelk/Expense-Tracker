import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

        // Prints menu of options
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

    // Using the database connection and the scanner class it will create an expense
    public static void addExpense(Database database, Scanner scanner) {
        System.out.println("Please give us the following information: ");

        // Gets the description
        System.out.print("Description: ");
        String description = scanner.nextLine();

        // Gets the category
        System.out.print("\nCategory: ");
        String category = scanner.nextLine();

        // Gets the amount of the expense
        System.out.print("\nAmount: ");
        BigDecimal amount = new BigDecimal( scanner.nextLine());

        // Confirms whether the purchase was today. If not you can set the date
        System.out.print("Did you make this purchase today (Yes or No): ");
        String choice = scanner.nextLine();
        LocalDate date = null;
        if (choice.equalsIgnoreCase("yes")){
            date = LocalDate.now();
        } else {
            System.out.println("Enter the following information about the expense.");
            // Gets the year
            System.out.print("Year: (ex. 2026): ");
            int year = scanner.nextInt();

            // Gets the month
            System.out.print("\nMonth: (1-12): ");
            int month = scanner.nextInt();

            // Gets the day
            System.out.print("\nDay: ");
            int day = scanner.nextInt();

            // Sets it all to a LocalDate object
            date = LocalDate.of(year, month, day);
        }

        database.addExpense(description, category, amount, date);
    }

    // gets the items from the database and then prints it out
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

    // You can update all parts of the expense in the event something is wrong
    public static void updateExpense(Database database, Scanner scanner){
        viewExpenses(database);
        System.out.print("Please choose the ID of the expense you want to upate: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Please input the following information.");

        //Similar process to AddExpense
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

        // Updates the database with the new information
        database.updateExpense(id, description, category, amount, date);
    }

    // Using the ID of the expense you can delete the expense from the DB
    public static void deleteExpense(Database database, Scanner scanner) {
        viewExpenses(database);
        System.out.print("Please choose the ID of the expense you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        database.deleteExpense(id);
    }

    // Using the SUM and AVG SQL statements this will print out a summary of all expenses
    public static void viewSummary(Database database) {
        ExpenseSummary expenseSummary = database.getExpenseSummary();
        System.out.printf("%nTotal spent: %.2f%nAverage spent: %.2f%n", expenseSummary.getTotal(), expenseSummary.getAverage());
    }
}

