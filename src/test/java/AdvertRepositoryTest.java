import Model.Advert;
import Model.User;
import jdbc.AdvertRepository;
import jdbc.CreationDeletion;
import jdbc.UserRepository;
import org.testng.Assert;
import org.testng.annotations.*;
import java.sql.SQLException;
import static jdbc.CreationDeletion.*;


public class AdvertRepositoryTest {
    @BeforeSuite
    public void fillUsersAndAdvertsTables() throws SQLException {
        createUsersTable();
        UserData[] usersDataFromJson = DataFromJson.getDataFromJson();
        for (int i = 0; i < usersDataFromJson.length; i++) {
            UserRepository.addUser(usersDataFromJson[i].getUser());
        }
        createAdvertsTable();
        AdvertData[] advertsDataFromJson = DataFromJson.getAdvertsDataFromJson();
        for (int i = 0; i < advertsDataFromJson.length; i++) {
            AdvertRepository.addNewAdvert(advertsDataFromJson[i].getAdvert(), usersDataFromJson[i].getUser());
        }
    }

    @DataProvider
    public static Object[][] getAdvertData() {
        AdvertData[] advertsDataFromJson = DataFromJson.getAdvertsDataFromJson();
        Object[][] objects = new Object[advertsDataFromJson.length][];
        for (int i = 0; i < advertsDataFromJson.length; i++) {
            objects[i] = new Object[]{advertsDataFromJson[i].getAdvert()};
        }
        return objects;
    }

    @Test(priority = 1)
    void testGetAllAdverts() throws SQLException {
        Assert.assertEquals(AdvertRepository.getAllAdvertises().size(), getAdvertData().length);
    }

    @Test(priority = 2)
    void testAddNewAdvert() throws SQLException {
        User user = new User("Vasya", "Tkachuk", "gfhdj@akfjdh", "11111111");
        UserRepository.addUser(user);
        Advert advert = new Advert("Help", "My dog is missing", user);
        int count = CreationDeletion.getCountAdverts();
        AdvertRepository.addNewAdvert(advert, user);
        Assert.assertEquals(CreationDeletion.getCountAdverts(), count+1);
    }

    @Test (priority = 2)
    void testDeleteAdvert()throws SQLException{
        Advert advert = new Advert(2,"Help", "My dog is missing", User.getUserById(4));
        int count = CreationDeletion.getCountAdverts();
        AdvertRepository.deleteAdvert(advert, User.getUserById(4));
        Assert.assertEquals(CreationDeletion.getCountAdverts(), count-1);
    }

    @DataProvider(name = "update")
    public static Object[][] getAdvertForUpdating() throws SQLException {
            Advert advert = new Advert("Warning", "Cat is missing", User.getUserById(1));
             return new Object[][]{{advert}};
    }

    @Test(dataProvider = "update", priority = 2)
    void testUpdateAdvert(Advert advert) throws SQLException {
        Advert advert1 = new Advert(1, "All good", "Cat is already at home");
        String title = advert.getAdvertTitle();
        AdvertRepository.updateAdvert(advert1);
        String title1 = advert1.getAdvertTitle();
        Assert.assertNotEquals(title, title1);
    }
    
    @AfterSuite
    void removeUsersAndAdvertsTables() throws SQLException {
        deleteAdvertsTable();
        deleteUsersTable();
    }

}
