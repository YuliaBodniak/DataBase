package Model;

import Util.PostgresConnection;

import java.sql.*;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;


    public User(int id, String name, String surname, String email, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User() {
    }

    public User(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                phone == user.phone &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    public static int getUserId(User user) throws SQLException {
        String GET_USER_ID = "SELECT * FROM Users WHERE name = '" + user.getName() + "'" + "AND surname = '" + user.getSurname() + "'" + "AND email = '" + user.getEmail() + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_USER_ID);
        int userId = 0;
        while (rs.next()) {
            userId = rs.getInt("id");
        }
        return userId;
    }

    public static User getUserById(int id) throws SQLException {
        String SELECT_USER = "SELECT * FROM Users WHERE id = ?";
        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            rs = preparedStatement.executeQuery();
        }
        User user = null;
        while (rs.next()) {
            user = new User();
            user.name = rs.getString("name");
            user.surname = rs.getString("surname");
            user.email = rs.getString("email");
            user.phone = rs.getString("phone");
        }
        user.setId(User.getUserId(user));
        return user;

    }
}
