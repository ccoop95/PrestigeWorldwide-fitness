package midterm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Database Utility Class
 *
 * @author Chance Cooper
 */
public class DBUtils {
    /**
     * Utility method used to create a Database Connection
     *
     * @return the Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        String server = "ipro.lambton.on.ca";
        String username = "root";
        String password = "muttonchops987";
        String jdbc = String.format("jdbc:mysql://%s/%s", server, "fitness");
        return DriverManager.getConnection(jdbc, username, password);
    }
}
