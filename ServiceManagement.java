import java.sql.*;
import java.util.Scanner;

class ServiceManagement {
    private Connection conn;
    private Scanner scanner;

    public ServiceManagement(Connection conn, Scanner scanner) {
        this.conn = conn;
        this.scanner = scanner;
    }

    public void manageServices() {
        while (true) {
            System.out.println("\nService Management");
            System.out.println("[1] Add Service");
            System.out.println("[2] View Services");
            System.out.println("[3] Update Service");
            System.out.println("[4] Delete Service");
            System.out.println("[5] Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addService();
                case 2 -> viewServices();
                case 3 -> updateService();
                case 4 -> deleteService();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addService() {
        try {
            System.out.print("Enter service name: ");
            String name = scanner.nextLine();
            System.out.print("Enter service hourly rate: ");
            double hourly_rate = scanner.nextDouble();
            scanner.nextLine();
            
            String sql = "insert into services (sname, hourly_rate) values (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setDouble(2, hourly_rate);
                stmt.executeUpdate();
                System.out.println("Service added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewServices() {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("select * from services")) {
            System.out.println("\nServices:");
            while (rs.next()) {
                System.out.println(rs.getInt("s_id") + " - " + rs.getString("sname") + " ($" + rs.getDouble("hourly_rate") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateService() {
        try {
            System.out.print("Enter service ID to update: ");
            int serviceId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new service name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new hourly rate: ");
            double newHourly_rate = scanner.nextDouble();
            scanner.nextLine();
            
            String sql = "update services set sname = ?, hourly_rate = ? where s_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newName);
                stmt.setDouble(2, newHourly_rate);
                stmt.setInt(3, serviceId);
                stmt.executeUpdate();
                System.out.println("Service updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteService() {
        try {
            System.out.print("Enter service ID to delete: ");
            int serviceId = scanner.nextInt();
            scanner.nextLine();
            
            String sql = "delete from services where s_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, serviceId);
                stmt.executeUpdate();
                System.out.println("Service deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
