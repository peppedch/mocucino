package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Registrazione driver, vedi slide ingegnere De Luca sezione "Java"
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

