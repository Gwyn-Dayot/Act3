import java.sql.*;
import java.util.Scanner;

public class InvoiceManagementCLI {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vainvoicesysdb";
    private static final String USER = "root";
    private static final String PASSWORD = "xxxx";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully.");
            runEventLoop(conn);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    private static void runEventLoop(Connection conn) {
        while (true) {
            System.out.println("\nVirtual Assistant Invoice System");
            System.out.println("Choose: ");

            System.out.println("[1] Client Management");
            System.out.println("[2] Service Management");
            System.out.println("[3] Invoice Management");
            System.out.println("[4] Exit ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> manageClients(conn);
                case 2 -> manageServices(conn);
                case 3 -> manageInvoices(conn);
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Select again");
            }
        }
    }

    private static void manageClients(Connection conn) {
        while (true) { 
            System.out.println("\n Client Management");
            System.out.println("Choose: ");

            System.out.println("[1] Add Client");
            System.out.println("[2] View Client");
            System.out.println("[3] Update Client");
            System.out.println("[4] Delete Client");
            System.out.println("[5] Back to Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addClient(conn);
                case 2 -> viewClients(conn);
                case 3 -> updateClient(conn);
                case 4 -> deleteClient(conn);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again");
            }
        }
    }

    
}   