package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Retrieves contact data from the contacts table
 */
public class DBContact {

    /**
     * Retrieves all contacts
     * @return An observable list of all contacts in the contacts table
     */
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {
            String sql = "select * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactID,contactName,email);
                contactList.add(c);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
