package jdbc;
import Util.PostgresConnection;

import java.sql.*;

public class CreationDeletion {

    public static void changeBase(String string){
        try (Connection connection = PostgresConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.getStackTrace();
        }
    }
    public static void createUsersTable(){
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS Users (id serial primary key, name varchar(100) not null, surname varchar(100) not null, email varchar(100) not null, phone varchar(20) not null)";
        changeBase(CREATE_USERS_TABLE);
    }
    public static void deleteUsersTable(){
        String DELETE_USERS_TABLE = "DROP TABLE Users";
        changeBase(DELETE_USERS_TABLE);
    }
    public static void createAdvertsTable(){
        String CREATE_ADVERT_TABLE = "CREATE TABLE IF NOT EXISTS Adverts (advert_id serial primary key, advert_title varchar(50) not null, description varchar(300), creation_date timestamp, update_date timestamp, user_id integer not null REFERENCES Users (id))";
        changeBase(CREATE_ADVERT_TABLE);
    }
    public static void deleteAdvertsTable(){
        String DELETE_ADVERT_TABLE = "DROP TABLE Adverts";
        changeBase(DELETE_ADVERT_TABLE);
    }

    public static int getCount() throws SQLException {
        String select = "select count(*) from Users";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(select);
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    public static int getCountAdverts() throws SQLException {
        String select = "select count(*) from Adverts";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(select);
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
}
