import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class DataFromJson {
    private String name;
    private String surname;
    private String email;
    private String phone;

    public DataFromJson(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
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

    public static final String USERS_DATA = "src/test/resources/users.json";
    public static final String ADVERTS_DATA = "src/test/resources/adverts.json";


    public static UserData[] getDataFromJson(){
        Reader reader = null;
        try {
            reader = new FileReader(USERS_DATA);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        Gson gson = new Gson();
        UserData[] usersDataFromJson = gson.fromJson(reader, UserData[].class);
        return usersDataFromJson;
        }

    public static AdvertData[] getAdvertsDataFromJson(){
        Reader reader = null;
        try {
            reader = new FileReader(ADVERTS_DATA);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        AdvertData[] advertsDataFromJson = gson.fromJson(reader, AdvertData[].class);
        return advertsDataFromJson;
    }

}
