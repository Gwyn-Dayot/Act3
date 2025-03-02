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

            ClientManagement clientManagement = new ClientManagement(conn, scanner);
            ServiceManagement serviceManagement = new ServiceManagement(conn, scanner);
            InvoiceManagement invoiceManagement = new InvoiceManagement(conn, scanner);

            runEventLoop(clientManagement, serviceManagement, invoiceManagement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }    

    public static void runEventLoop(ClientManagement clientManagement, ServiceManagement serviceManagement, InvoiceManagement invoiceManagement) {
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
                case 1 -> clientManagement.manageClients();
                case 2 -> serviceManagement.manageServices();
                case 3 -> invoiceManagement.manageInvoices();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Select again");
            }
        }
    }
}   