package Model;

import Util.PostgresConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;

public class Advert {
    private int advertId;
    private String advertTitle;
    private String description;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private int user_id;
    private User user;

    public Advert() {
    }

    public Advert(String advertTitle, String description, User user) {
        this.advertTitle = advertTitle;
        this.description = description;
        this.user = user;
    }

    public Advert(int advertId, String advertTitle, String description, User user) {
        this.advertId = advertId;
        this.advertTitle = advertTitle;
        this.description = description;
        this.user = user;
    }

    public Advert(int advertId, String advertTitle, String description) {
        this.advertId = advertId;
        this.advertTitle = advertTitle;
        this.description = description;
    }

    public int getAdvertId() {
        return advertId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAdvertId(int advertId) {
        this.advertId = advertId;
    }

    public String getAdvertTitle() {
        return advertTitle;
    }

    public void setAdvertTitle(String advertTitle) {
        this.advertTitle = advertTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advert advert = (Advert) o;
        return advertId == advert.advertId &&
                Objects.equals(advertTitle, advert.advertTitle) &&
                Objects.equals(description, advert.description) &&
                Objects.equals(creationDate, advert.creationDate) &&
                Objects.equals(updateDate, advert.updateDate) &&
                Objects.equals(user, advert.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertId, advertTitle, description, creationDate, updateDate, user);
    }

    @Override
    public String toString() {
        return "Advert{" +
                "advertId=" + advertId +
                ", advertTitle='" + advertTitle + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                '}';
    }

    public static int selectAdvertId(Advert advert, User user) throws SQLException {
        String GET_USER_ID = "SELECT * FROM Adverts WHERE advert_title = '" + advert.getAdvertTitle()+ "'" + "AND description = '" + advert.getDescription() + "'" + "AND user_id = '" + user.getId()+ "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_USER_ID);
        int advertId = 0;
        while (rs.next()) {
            advertId = rs.getInt("advert_id");
        }
        return advertId;
    }

    //    public static boolean isAdvertPresent(Advert advert, User user) throws SQLException {
//        String sql = "SELECT * FROM Adverts WHERE advert_id = " + selectAdvertId(advert, user) + "AND advert_title = " + advert.getAdvertTitle()+ "AND description = '" + advert.getDescription() + "'AND id = '" + user.getId()+ "'";
//        Connection connection = PostgresConnection.getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery(sql);
//        while (rs.next()) {
//            return true;
//        }
//        return false;
//    }
//
}
