package database;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBManager.openConnection();
            System.out.println(" Connessione al database riuscita!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Errore di connessione:");
            e.printStackTrace();
        }
    }
}
