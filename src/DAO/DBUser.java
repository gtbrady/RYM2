package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves user data from the users table
 */
public class DBUser {

    /**
     * Retrieves all users
     * @return An observable list of all users in the users table
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "select \n" +
                    "user_ID,\n" +
                    "user_Name,\n" +
                    "Password\n" +
                    "from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("user_ID");
                String userName = rs.getString("user_Name");
                String password = rs.getString("Password");
                User u = new User(userID, userName,password);
                userList.add(u);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
