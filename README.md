# Expense Tracker

## Overview

This project is an expense tracker written in Java that uses a PostgreSQL relational database to store expense information.

The program allows a user to add, view, update, and delete expenses. Each expense contains a description, category, amount, and date. The program also uses SQL aggregate functions to calculate the total amount spent and the average expense.

The purpose of this project was to learn how to integrate a relational database with a software application. I wanted to gain experience using SQL commands from Java and learn how JDBC can be used to send commands to a PostgreSQL database and process the results.

[Software Demo Video](https://youtu.be/zUUFVftRabU)

## Relational Database

I used PostgreSQL as the relational database for this project. The Java application communicates with PostgreSQL using JDBC.

The database contains an `expenses` table with the following columns:

- `id` - A unique identifier and primary key for each expense.
- `description` - A description of the expense.
- `category` - The category associated with the expense.
- `amount` - The monetary amount of the expense.
- `expense_date` - The date the expense occurred.

The program creates the `expenses` table if it does not already exist.

The application demonstrates the four basic CRUD database operations:

- **Create:** Inserts new expenses using an SQL `INSERT` statement.
- **Read:** Retrieves expenses using an SQL `SELECT` statement.
- **Update:** Modifies existing expenses using an SQL `UPDATE` statement.
- **Delete:** Removes expenses using an SQL `DELETE` statement.

The program also uses the SQL aggregate functions `SUM` and `AVG` to calculate the total amount spent and the average expense.

## Development Environment

I developed this project using Visual Studio Code on Windows.

The project uses the following technologies:

- Java
- PostgreSQL
- JDBC
- Maven
- pgAdmin 4
- Visual Studio Code

Maven is used to manage the PostgreSQL JDBC driver dependency.

## Useful Websites

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)
- [Java JDBC Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
- [Maven Documentation](https://maven.apache.org/guides/)

## Future Work

Some features I would like to add in the future include:

- Allow users to search and filter expenses by category.
- Allow users to filter expenses within a specific date range.
- Add additional spending statistics and reports.
- Improve input validation and error handling.
- Add the ability to create spending budgets for different categories.