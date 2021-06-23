package utils;


import java.sql.*;
/** class establishes connection to database. */
public class DB_connection {

    private static final String protocol = "JDBC";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07wmF";
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    private static final String userName = "U07wmF";
    private static final String password = "53689153748";
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static Connection conn = null;
    /** class creates connection. */
    public static Connection getConnection() {

        try {

            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successful");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }


        return conn;

    }
    /** class closes connection  */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("connection closed");
        } catch (SQLException e) {
            System.out.println("ERROR" + e.getMessage());
        }

    }

}


