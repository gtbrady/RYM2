package DAO;

import Model.Appointment;
import Model.Contact;
import Model.RepTwo;
import Utility.TimeManipulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves the schedule for the given contact. Represented by a repTwo object.
 * Retrieves data from the appointments and contacts tables
 */
public class DBRepTwo {

    /**
     * Retrieves all repTwo objects
     * @param c The contact the user selects
     * @return An observable list of repTwo objects
     */
    public static ObservableList<RepTwo> getRepTwo(Contact c) {
        ObservableList<RepTwo> repTwo = FXCollections.observableArrayList();
        ObservableList<RepTwo> repTwoConverted = FXCollections.observableArrayList();
        try {
            String sql = """
                    select\s
                    contact_name as Contact,
                    Appointment_ID,
                    title as Title,
                    type as Type,
                    description as Description,
                    start as 'Start Date / Time',
                    end as 'End Date / Time',
                    Customer_ID
                    from appointments\s
                    inner join contacts on contacts.Contact_ID = appointments.Contact_ID
                    where appointments.Contact_ID = ?\s
                    order by contact, start""";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,c.getContactID());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contactName = rs.getString("Contact");
                int aID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String start = rs.getString("Start Date / Time");
                String end = rs.getString("End Date / Time");
                int cID = rs.getInt("Customer_ID");
                RepTwo two = new RepTwo(contactName, aID, title, type, description, start, end, cID);
                repTwo.add(two);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        for(RepTwo r: repTwo) {
            RepTwo b = TimeManipulation.dbtoSystemRepTwo(r);
            repTwoConverted.add(b);
        }

        return repTwoConverted;
    }
}
