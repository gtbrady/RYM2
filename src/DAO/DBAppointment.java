package DAO;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Utility.TimeManipulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Retrieves appointment data from the appointments table
 */
public class DBAppointment {

    /**
     * Retrieves all appointments
     * @return An observable list of all appointments in the appointments table
     */
    public static ObservableList<Appointment> getAllAppointments () {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> convertedAppointments = FXCollections.observableArrayList();
        try {
            String sql = "select \n" +
                    "appointments.Appointment_ID as 'Appointment ID',\n" +
                    "customers.Customer_Name as 'Customer Name',\n" +
                    "appointments.Title,\n" +
                    "appointments.Description,\n" +
                    "appointments.Location,\n" +
                    "contacts.Contact_Name as Contact,\n" +
                    "appointments.Type,\n" +
                    "appointments.Start as 'Start Date and Time',\n" +
                    "appointments.End as 'End Date and Time',\n" +
                    "appointments.Customer_ID,\n" +
                    "appointments.User_ID\n" +
                    "\n" +
                    "from appointments\n" +
                    "join customers on customers.Customer_ID = appointments.Customer_ID\n" +
                    "join contacts on appointments.Contact_ID = contacts.Contact_ID order by Start, Appointment_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int aID = rs.getInt("Appointment ID");
                String name = rs.getString("Customer Name");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact");
                String type = rs.getString("Type");
                String start = rs.getString("Start Date and Time");
                String end = rs.getString("End Date and Time");
                int cID = rs.getInt("Customer_ID");
                int uID = rs.getInt("User_ID");


                Appointment a = new Appointment(aID, name, title, description, location, contact, type,
                        start, end, cID, uID);
                allAppointments.add(a);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        for(Appointment a: allAppointments) {
            Appointment b = TimeManipulation.dbtoSystem(a);
            convertedAppointments.add(b);
        }

        return convertedAppointments;
    }

    /**
     * Inserts the provided appointment into the appointments table
     * @param a The appointment provided by the user
     * @param c The customer provided by the user
     * @param t The contact provided by the user
     * @return The number of rows inserted
     */
    public static int addAppointment(Appointment a, Customer c, Contact t) {
        int addConfirm = -1;
        try {
            String sql = """
                    insert into appointments 
                    (Appointment_ID, Title,Description,Location,Type,Start,End,Create_Date,
                    Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID)
                    values (NULL,?,?,?,?,?,?,?,?,?,?,?,?,?)
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,a.getTitle());
            ps.setString(2, a.getDescription());
            ps.setString(3,a.getLocation());
            ps.setString(4, a.getType());
            ps.setString(5,a.getStartDT());
            ps.setString(6,a.getEndDT());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, Model.AuthorizedUser.getAuthorizedName());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(11, c.getCustomerID());
            ps.setInt(12,Model.AuthorizedUser.getAuthorizedID());
            ps.setInt(13, t.getContactID());

            addConfirm = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addConfirm;
    }

    /**
     * Updates the provided appointment in the appointments table
     * @param a The appointment provided by the user
     * @param c The customer provided by the user
     * @param t The contact provided by the user
     * @return The number of rows updated
     */
    public static int editAppointment(Appointment a, Customer c, Contact t) {
        int editConfirm = -1;
        try {
            String sql = """
                    update appointments set
                    Title = ?,
                    Description = ?,
                    Location = ?,
                    Type = ?,
                    Start = ?,
                    End = ?,
                    Last_Update = ?,
                    Last_Updated_By = ?,
                    Customer_ID = ?,
                    User_ID = ?,
                    Contact_ID = ?
                    where Appointment_ID = ?;
                    """;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,a.getTitle());
            ps.setString(2,a.getDescription());
            ps.setString(3,a.getLocation());
            ps.setString(4,a.getType());
            ps.setString(5,a.getStartDT());
            ps.setString(6,a.getEndDT());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(9, c.getCustomerID());
            ps.setInt(10,Model.AuthorizedUser.getAuthorizedID());
            ps.setInt(11, t.getContactID());
            ps.setInt(12,a.getAppointmentID());

            editConfirm = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return editConfirm;
    }

    /**
     * Deletes the provided appointment in the appointments table
     * @param a The appointment provided by the user
     * @return The number of rows deleted
     */
    public static int deleteAppointment(Appointment a) {
        int deleteConfirm = -1;
        try {
            String sql = "delete from appointments where appointment_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,a.getAppointmentID());
            deleteConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteConfirm;
    }
}

