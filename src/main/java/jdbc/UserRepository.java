package jdbc;
import Model.User;
import Util.PostgresConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static User addUser(User user) throws SQLException {
        if (isUserPresent(user)) {
            throw new SQLException("This user is already in the table!");
        }
        String ADD_NEW_USER = "INSERT INTO Users (name, surname, email, phone)"
                + "values(?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
            }
            System.out.println("User has successfully added!");
        }
        return user;
    }

    public static boolean updateUser(User user) throws SQLException {
        String UPDATE_USER = "UPDATE Users SET name=?, surname=?, email=?, phone=?" +
                "WHERE id = ?;";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
            return (preparedStatement.executeUpdate() != 0);
        }
    }

    public static void deleteUser(User user) throws SQLException {
        String DELETE_USER = "DELETE FROM Users WHERE id = ?";
        Connection connection = PostgresConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.execute();
        connection.close();
        System.out.println("User is successfully deleted!");

    }

    public static boolean isUserPresent(User user) throws SQLException {
        String isUserPresent = "SELECT * FROM Users WHERE name = '" + user.getName() +
                "'" + "AND surname = '" + user.getSurname() + "'" + "AND email = '" + user.getEmail() + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        try (ResultSet rs = statement.executeQuery(isUserPresent)) {
            while (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public static List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        String GET_ALL_USERS = "SELECT * FROM Users";
        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery(GET_ALL_USERS);
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            allUsers.add(user);
        }
        rs.close();
        return allUsers;
    }

}
