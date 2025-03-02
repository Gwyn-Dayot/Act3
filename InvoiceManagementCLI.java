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
            System.out.println("\nInvoice Management System:");
            System.out.println("1. Add Client");
            System.out.println("2. View Clients");
            System.out.println("3. Create Invoice");
            System.out.println("4. View Invoices");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addClient(conn);
                case 2 -> viewClients(conn);
                case 3 -> createInvoice(conn);
                case 4 -> viewInvoices(conn);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addClient(Connection conn) {
        try {
            System.out.print("Enter client name: ");
            String name = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();
            
            String sql = "INSERT INTO clients (cname, phone) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, phone);
                stmt.executeUpdate();
                System.out.println("Client added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewClients(Connection conn) {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {
            System.out.println("\nClients:");
            while (rs.next()) {
                System.out.println(rs.getInt("c_id") + " - " + rs.getString("cname") + " (" + rs.getString("phone") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createInvoice(Connection conn) {
        try {
            System.out.print("Enter client ID: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter issue date (YYYY-MM-DD): ");
            String issueDate = scanner.nextLine();
            System.out.print("Enter total amount: ");
            double totalAmount = scanner.nextDouble();
            
            String sql = "INSERT INTO invoices (c_id, issue_date, totalAmount) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, clientId);
                stmt.setString(2, issueDate);
                stmt.setDouble(3, totalAmount);
                stmt.executeUpdate();
                System.out.println("Invoice created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewInvoices(Connection conn) {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM invoices")) {
            System.out.println("\nInvoices:");
            while (rs.next()) {
                System.out.println("Invoice ID: " + rs.getInt("i_id") + ", Client ID: " + rs.getInt("c_id") + ", Issue Date: " + rs.getDate("issue_date") + ", Total Amount: " + rs.getDouble("totalAmount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}