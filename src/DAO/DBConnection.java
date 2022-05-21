package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Starts and closes the connection to the MySQL database
 */
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//rds-mysql-gtyrm2-519.cd8kkachwhvx.us-east-2.rds.amazonaws.com:3306/";
    private static final String dbName = "mhs_schedule";

    private static final String connectionTZ = "?connectionTimeZone=SERVER";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + connectionTZ;
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "admin519";
    private static final String password = "database868";
    private static Connection conn = null;

    /**
     * Starts the initial connection to the database
     * @return The connection object
     */
    public static Connection startConnection () {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Retrieves the existing connection
     * @return The connection object
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Closes the exiting connection
     */
    public static void closeConnection() {
        try{
            conn.close();
            } catch (Exception e) {
        }
    }
}
