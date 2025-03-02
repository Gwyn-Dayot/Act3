import java.sql.*;
import java.util.Scanner;

class ClientManagement {
    private Connection conn;
    private Scanner scanner;

    public ClientManagement(Connection conn, Scanner scanner) {
        this.conn = conn;
        this.scanner = scanner;
    }

    public void manageClients() {
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

    private void addClient(Connection conn) {
        try {
            System.out.println("Enter client name: ");
            String name = scanner.nextLine();

            System.out.println("Enter phone number: ");
            String phone = scanner.nextLine();

            String sql = "insert into clients (cname, phone) values (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, phone);
                    stmt.executeUpdate();
            System.out.println("]\nClient added");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewClients(Connection conn) {
        try ( Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("select *  from clients")) {
            System.out.println("\nClients");
            while (rs.next()) {
                System.out.println(rs.getInt("c_id") + " - " + rs.getString("cname") + " (" + rs.getString("phone") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClient(Connection conn) {
        try {
            System.out.println("Enter client ID to update: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter new client name: ");
            String newName = scanner.nextLine();
            
            System.out.println("Enter new phone number: ");
            String newPhone = scanner.nextLine();

            String sql = "update clients set cname = ?, phone = ? where c_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newName);
                stmt.setString(2, newPhone);
                stmt.setInt(3, clientId);
                int rowsUpdated =stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Client updated");
                    } else {
                        System.out.println("Client not found");
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient(Connection conn) {
        try {
            System.out.println("Enter client ID to delete: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();

            String sql = "delete from clients where c_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, clientId);
                int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Client deleted");
                    } else {
                        System.out.println("Client not found");
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}