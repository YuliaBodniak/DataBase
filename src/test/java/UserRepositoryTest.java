import Model.User;
import jdbc.CreationDeletion;
import jdbc.UserRepository;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.sql.SQLException;
import static jdbc.CreationDeletion.*;
import static jdbc.UserRepository.getAllUsers;

public class UserRepositoryTest {
    @BeforeSuite
    public void fillUsersTable() throws SQLException {
        createUsersTable();
        UserData[] usersDataFromJson = DataFromJson.getDataFromJson();
        for (int i = 0; i < usersDataFromJson.length; i++) {
            UserRepository.addUser(usersDataFromJson[i].getUser());
        }
    }

    @DataProvider
    public static Object[][] getUserData() {
        UserData[] usersDataFromJson = DataFromJson.getDataFromJson();
        Object[][] objects = new Object[usersDataFromJson.length][];
        for (int i = 0; i < usersDataFromJson.length; i++) {
            objects[i] = new Object[]{usersDataFromJson[i].getUser()};
        }
        return objects;
    }

    @Test
    void testGetAllUsers() throws SQLException {
        getAllUsers();
        Assert.assertEquals(getAllUsers().size(), getUserData().length);
    }

    @DataProvider(name = "add")
    public static Object[][] getUserForAdding() {
        User user = new User("Vasya", "Tkachuk", "gfhdj@akfjdh", "11111111");
        return new Object[][]{{user}};
    }

    @Test(dataProvider = "add")
    void testAddUser(User user) throws SQLException {
        int count = CreationDeletion.getCount();
        UserRepository.addUser(user);
        Assert.assertEquals(CreationDeletion.getCount(), count+1);
    }

    @DataProvider(name = "update")
    public static Object[][] getUserForUpdating() throws SQLException {
        User user = User.getUserById(2);
        return new Object[][]{{user}};
    }

    @Test(dataProvider = "update")
    void testUpdateUser(User user) throws SQLException {
        User user1 = new User(2,"Dasha", "Ivanova", "ivanova@com.ua", "0505263748");
        UserRepository.updateUser(user1);
        Assert.assertNotEquals(user, user1);
    }

    @DataProvider(name = "delete")
    public static Object[][] getUserForDeleting() throws SQLException {
        User user = User.getUserById(1);
        return new Object[][]{{user}};
    }
    @Test(dataProvider = "delete")
    void testDeleteUser(User user) throws SQLException {
        int count = CreationDeletion.getCount();
        UserRepository.deleteUser(user);
        Assert.assertEquals(CreationDeletion.getCount(), count-1);
    }

    @AfterSuite
    void removeUsersTable() throws SQLException {
        deleteUsersTable();
    }
}