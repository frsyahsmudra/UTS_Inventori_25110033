package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL =
            "jdbc:mysql://localhost:3306/db_inventori";

    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}