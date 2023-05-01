import util.Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static util.UseCaseImpl.*;

/**
 * Class to provide Command Line Interface
 */
public class Driver {
    private static final int EXIT = 10;
    private static boolean isContinued = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (isContinued) {
            printMenu();
            System.out.print("Which operation do you want: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("please input a number");
                continue;
            }

            try {
                handleChoice(choice);
            } catch (SQLException e) {
                System.out.println("cannot execute operation, try again");
            }
            if (!isContinued) break;

            System.out.println();
            System.out.print("Do you want to continue? Answer Y/N: ");
            String answer = scanner.next();
            if (answer.toLowerCase().indexOf("n") == 0) {
                isContinued = false;
            }
            System.out.println();
        }
        System.out.println("Exit successfully");
    }

    private static void printMenu() {
        System.out.println("====== Options ======");
        System.out.println("1. Top apartments that have lowest price");
        System.out.println("2. Raise the rent price in a building due to inflation");

        System.out.println("3. Add a maintenance request for an apartment");
        System.out.println("4. Mark a maintenance request as finished");
        System.out.println("5. Show in-process maintenance requests in a building with a list of staff involved");

        System.out.println("6. Add a new tenant's rent");
        System.out.println("7. Remove a tenant");
        System.out.println("8. Show all tenants that have overdue payment");
        System.out.println("9. Show the total amount of money that a tenant owes (a owner or more?)");
        System.out.println("10. Exit");
    }

    private static void handleChoice(int choice) throws SQLException {
        // Echo the input back to the console
        if (choice <= 0 || choice > EXIT) {
            System.out.println("Invalid choice, choose between 1 and " + EXIT);
            return;
        }
        if (choice == EXIT) {
            isContinued = false;
            return;
        }
        // handle logic
        Connection connection = Utility.getConnection();
        try {
            connection.setAutoCommit(false);
            switch (choice) {
                case 1 -> System.out.println(1);
                case 2 -> System.out.println(2);
                case 3 -> addRequest();
                case 4 -> finishRequest();
                case 5 -> showUnfinishedRequests();
                case 6 -> System.out.println(6);
                case 7 -> System.out.println(7);
                case 8 -> System.out.println(8);
                case 9 -> System.out.println(9);
            }
        } catch (SQLException e) {
            connection.rollback();
        }

    }
}
