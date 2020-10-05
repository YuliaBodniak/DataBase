package jdbc;

import Model.Advert;
import Model.User;
import Util.PostgresConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Model.Advert.selectAdvertId;

public class AdvertRepository {
    public static Advert addNewAdvert(Advert advert, User user) {
        String ADD_NEW_ADVERT = "INSERT INTO Adverts (advert_title, description, creation_date, user_id)"
                + "values(?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_ADVERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, advert.getAdvertTitle());
            preparedStatement.setString(2, advert.getDescription());
            preparedStatement.setObject(3, LocalDate.now());
            preparedStatement.setObject(4, User.getUserId(user));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                advert.setAdvertId(rs.getInt("advert_id"));
            }
            System.out.println("Advertise is successfully added!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return advert;
    }


    public static boolean updateAdvert(Advert advert) throws SQLException {
        String EDIT_ADVERT = "UPDATE Adverts SET advert_title=?, description=?, update_date=?" +
                "WHERE advert_id = ?;";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ADVERT);
            preparedStatement.setString(1, advert.getAdvertTitle());
            preparedStatement.setString(2, advert.getDescription());
            preparedStatement.setObject(3, LocalDate.now());
            preparedStatement.setInt(4, advert.getAdvertId());
            return (preparedStatement.executeUpdate() != 0);
        }
    }

    public static void deleteAdvert(Advert advert, User user) throws SQLException {
        String DELETE_ADVERT = "DELETE FROM Adverts WHERE advert_id = ?";
        Connection connection = PostgresConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADVERT);
        preparedStatement.setInt(1, selectAdvertId(advert, user));
        preparedStatement.execute();
        connection.close();
        System.out.println("Advertise is successfully deleted!");

    }



    public static List<Advert> getAllAdvertises() throws SQLException {
        List<Advert> allAdverts = new ArrayList<>();
        String GET_ALL_ADVERTS = "SELECT * FROM Adverts";
        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery(GET_ALL_ADVERTS);
        while (rs.next()) {
            Advert advert = new Advert();
            advert.setAdvertId(rs.getInt("advert_id"));
            advert.setAdvertTitle(rs.getString("advert_title"));
            advert.setDescription(rs.getString("description"));
            advert.setCreationDate(rs.getDate("creation_Date").toLocalDate());
            advert.setUser_id(rs.getInt("user_id"));
            allAdverts.add(advert);
        }
        rs.close();
        return allAdverts;
    }
}

