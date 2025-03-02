import java.sql.*;
import java.util.Scanner;

public class InvoiceManagement {
    private Connection conn;
    private Scanner scanner;

    public InvoiceManagement(Connection conn, Scanner scanner) {
        this.conn = conn;
        this.scanner = scanner;
    }

    public void manageInvoices() {
        while (true) {
            System.out.println("\nInvoice Management:");
            System.out.println("1. Create Invoice");
            System.out.println("2. View Invoices");
            System.out.println("3. Update Invoice");
            System.out.println("4. Delete Invoice");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createInvoice();
                case 2 -> viewInvoices();
                case 3 -> updateInvoice();
                case 4 -> deleteInvoice();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createInvoice() {
        try {
            System.out.print("Enter client ID: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter issue date (YYYY-MM-DD): ");
            String issueDate = scanner.nextLine();
            System.out.print("Enter total amount: ");
            double totalAmount = scanner.nextDouble();

            String sql = "insert into invoices (c_id, issue_date, totalAmount) values (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, clientId);
                stmt.setString(2, issueDate);
                stmt.setDouble(3, totalAmount);
                stmt.executeUpdate();
                System.out.println("Invoice created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewInvoices() {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("select * from invoices")) {
            System.out.println("\nInvoices:");
            while (rs.next()) {
                System.out.println("Invoice ID: " + rs.getInt("i_id") + ", Client ID: " + rs.getInt("c_id") + 
                                   ", Issue Date: " + rs.getDate("issue_date") + ", Total Amount: " + rs.getDouble("totalAmount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInvoice() {
        try {
            System.out.print("Enter invoice ID to update: ");
            int invoiceId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new issue date (YYYY-MM-DD): ");
            String newIssueDate = scanner.nextLine();
            System.out.print("Enter new total amount: ");
            double newTotalAmount = scanner.nextDouble();

            String sql = "update invoices set issue_date = ?, totalAmount = ? where i_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newIssueDate);
                stmt.setDouble(2, newTotalAmount);
                stmt.setInt(3, invoiceId);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Invoice updated successfully.");
                } else {
                    System.out.println("Invoice not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteInvoice() {
        try {
            System.out.print("Enter invoice ID to delete: ");
            int invoiceId = scanner.nextInt();

            String sql = "delete from invoices where i_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, invoiceId);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Invoice deleted");
                } else {
                    System.out.println("Invoice not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}