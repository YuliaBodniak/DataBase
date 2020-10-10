package Util;
import java.sql.*;

public class PostgresConnection {
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "159753a";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }
}