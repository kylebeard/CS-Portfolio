import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class JDBCTest {
    
    private static final String DB_URL =
        "jdbc:mysql://localhost/SaleCo?user=java&password=duke"; // MySQL
        // "jdbc:sqlite:dbfile.db"; // SQLite
    
    public static void main(String... args) {
        // try-with-resources: catch exceptions, but also ensure that the connection
        // gets closed (with a finally, but I don't need to write it)
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // A Connection object represents a connection to the database
            
            // Ask the user for a product code to search for
            System.out.print("Product code: ");
            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine();
            
            // A Statement object represents a single SQL statement (surprise, surprise); this
            // is how we plug queries and things into the database
            // Basic use of a Statement like this is only for when the query does not
            // include any user input.
            /*
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(
                "SELECT P_DESCRIPT, P_PRICE "
                + "FROM PRODUCT "
                + "WHERE P_CODE = '" + code + "'");
            */
            // When the query does involve user input, use a PreparedStatement instead.
            PreparedStatement statement = conn.prepareStatement(
                "SELECT P_DESCRIPT, P_PRICE "
                + "FROM PRODUCT "
                + "WHERE P_CODE = ?");
            // The ? above is a placeholder for a value to be inserted
            
            // Plug the code in for the ? in the query
            statement.setString(1, code);
            
            System.out.println("\tStatement: " + statement);
            
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                // This loop runs once for every row in the result table
                String description;
                // Two different ways to get a data value from the row:
                // 1) By index (N.B.: indexed from ****1****, not zero for some reason)
                //code = results.getString(1);
                // 2) By column name
                description = results.getString("P_DESCRIPT");
                
                double price = results.getDouble("P_PRICE");
                
                System.out.printf("%s\n%s\n$%.2f\n\n", code, description, price);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Unable to connect to the database. :(", "Database error!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
