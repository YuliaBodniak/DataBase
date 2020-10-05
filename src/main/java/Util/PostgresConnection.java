package Util;

import Model.Advert;
import Model.User;
import jdbc.UserRepository;

import java.sql.*;

import static jdbc.AdvertRepository.*;
import static jdbc.CreationDeletion.*;
import static jdbc.UserRepository.*;

public class PostgresConnection {
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "159753a";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

    public static void main(String[] args) throws SQLException {
//        createAdvertsTable();
//        createUsersTable();
        deleteAdvertsTable();
        deleteUsersTable();
//        User user1 = new User("Masha","Ivanova", "ivanova@com.ua", "0505263748");
//        User user2 = new User (2,"Sveta", "Boychuk", "boychuk@com.ua", "0504294875");
//        User user3 = new User(1,"Dasha","Ivanova", "ivanova@com.ua", "0505263748");
//        Advert advert1 = new Advert ("Warning", "Cat is missing", user3);
//        Advert advert2 = new Advert(2, "It`s all good", "I have found my cat", user2);
//        Advert advert3 = new Advert(3, "All good", "Cat is already at home");
//        addUser(user1);
//        addUser(user2);
//        deleteUser(user1);
//        createAdvertsTable();
//       updateUser(user3);
//        addNewAdvert(advert1, user3);
//        updateAdvert(advert3);
//        deleteAdvert(advert1, user2);
//        System.out.println(getAllUsers());
//        System.out.println(advertId);
//        System.out.println(getAllAdvertises());
//        System.out.println(User.getUserId(user1));
//        System.out.println(user1.getId());
//        User user = User.getUserById(3);
//        System.out.println(user);
//        UserRepository.deleteUser(user);
//        System.out.println(getAllUsers());
    }
}