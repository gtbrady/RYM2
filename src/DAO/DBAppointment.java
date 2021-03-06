package DAO;

import Model.*;
import Utility.TimeManipulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBAppointment {

    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> convertedAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String sql = """
                    SELECT\s
                    appointments.appointment_ID as	"Appointment ID",
                    clients.client_name as "Client Name",
                    counselors.counselor_name as "Counselor Name",
                    appointments.counselor_id as "Counselor ID",
                    appointments.client_id as "Client ID",
                    appointments.detail_1 as "Detail_1",
                    appointments.detail_2 as "Detail_2",
                    appointments.description as "Description",
                    appointments.type as "Type",
                    appointments.start as "Start Date and Time",
                    appointments.end as "End Date and Time"
                    FROM appointments LEFT JOIN clients on appointments.Client_ID = clients.Client_ID
                    LEFT JOIN counselors on appointments.Counselor_ID = counselors.Counselor_ID;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int aID = rs.getInt("Appointment ID");
                int coID = rs.getInt("Counselor ID");
                String coName = rs.getString("Counselor Name");
                int clID = rs.getInt("Client ID");
                String clName = rs.getString("Client Name");
                String description = rs.getString("Description");
                AppointmentType type =AppointmentType.valueOf(rs.getString("Type"));
                String start = rs.getString("Start Date and Time");
                String end = rs.getString("End Date and Time");
                String detail1 = rs.getString("Detail_1");
                String detail2 = rs.getString("Detail_2");

                if(type == AppointmentType.Office) {
                    appointment = new OfficeAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    allAppointments.add(appointment);
                }
                else if (type == AppointmentType.Virtual) {
                    appointment = new VirtualAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    allAppointments.add(appointment);
                }
                else if (type == AppointmentType.Phone) {
                    appointment = new PhoneAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    allAppointments.add(appointment);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Appointment a: allAppointments) {
            String originalStart = a.getStartTime();
            String originalEnd = a.getEndTime();
            String updatedStart = TimeManipulation.stringUTS(originalStart);
            String updatedEnd = TimeManipulation.stringUTS(originalEnd);
            a.setStartTime(updatedStart);
            a.setEndTime(updatedEnd);
        }


        return allAppointments;
    }

    public static ObservableList<Appointment> getPhoneAppointments() {
        ObservableList<Appointment> phoneAppointments = FXCollections.observableArrayList();
        //ObservableList<nAppointment> convertedAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String sql = """
                    SELECT\s
                    appointments.appointment_ID as	"Appointment ID",
                    clients.client_name as "Client Name",
                    counselors.counselor_name as "Counselor Name",
                    appointments.counselor_id as "Counselor ID",
                    appointments.client_id as "Client ID",
                    appointments.detail_1 as "Detail_1",
                    appointments.detail_2 as "Detail_2",
                    appointments.description as "Description",
                    appointments.type as "Type",
                    appointments.start as "Start Date and Time",
                    appointments.end as "End Date and Time"
                    FROM appointments LEFT JOIN clients on appointments.Client_ID = clients.Client_ID
                    LEFT JOIN counselors on appointments.Counselor_ID = counselors.Counselor_ID
                    WHERE Type = "Phone";
                        """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int aID = rs.getInt("Appointment ID");
                int coID = rs.getInt("Counselor ID");
                String coName = rs.getString("Counselor Name");
                int clID = rs.getInt("Client ID");
                String clName = rs.getString("Client Name");
                String description = rs.getString("Description");
                AppointmentType type =AppointmentType.valueOf(rs.getString("Type"));
                String start = rs.getString("Start Date and Time");
                String end = rs.getString("End Date and Time");
                String detail1 = rs.getString("Detail_1");
                String detail2 = rs.getString("Detail_2");


                    appointment = new PhoneAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    phoneAppointments.add(appointment);
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TimeManipulation loop occurs here - need to write once bulk of rework completed - return converted list

        return phoneAppointments;
    }

    public static ObservableList<Appointment> getVirtualAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        //ObservableList<nAppointment> convertedAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String sql = """
                    SELECT\s
                    appointments.appointment_ID as	"Appointment ID",
                    clients.client_name as "Client Name",
                    counselors.counselor_name as "Counselor Name",
                    appointments.counselor_id as "Counselor ID",
                    appointments.client_id as "Client ID",
                    appointments.detail_1 as "Detail_1",
                    appointments.detail_2 as "Detail_2",
                    appointments.description as "Description",
                    appointments.type as "Type",
                    appointments.start as "Start Date and Time",
                    appointments.end as "End Date and Time"
                    FROM appointments LEFT JOIN clients on appointments.Client_ID = clients.Client_ID
                    LEFT JOIN counselors on appointments.Counselor_ID = counselors.Counselor_ID
                    WHERE type = "Virtual";
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int aID = rs.getInt("Appointment ID");
                int coID = rs.getInt("Counselor ID");
                String coName = rs.getString("Counselor Name");
                int clID = rs.getInt("Client ID");
                String clName = rs.getString("Client Name");
                String description = rs.getString("Description");
                AppointmentType type =AppointmentType.valueOf(rs.getString("Type"));
                String start = rs.getString("Start Date and Time");
                String end = rs.getString("End Date and Time");
                String detail1 = rs.getString("Detail_1");
                String detail2 = rs.getString("Detail_2");


                    appointment = new VirtualAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    allAppointments.add(appointment);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TimeManipulation loop occurs here - need to write once bulk of rework completed - return converted list

        return allAppointments;
    }

    public static ObservableList<Appointment> getOfficeAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        //ObservableList<nAppointment> convertedAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String sql = """
                    SELECT\s
                    appointments.appointment_ID as	"Appointment ID",
                    clients.client_name as "Client Name",
                    counselors.counselor_name as "Counselor Name",
                    appointments.counselor_id as "Counselor ID",
                    appointments.client_id as "Client ID",
                    appointments.detail_1 as "Detail_1",
                    appointments.detail_2 as "Detail_2",
                    appointments.description as "Description",
                    appointments.type as "Type",
                    appointments.start as "Start Date and Time",
                    appointments.end as "End Date and Time"
                    FROM appointments LEFT JOIN clients on appointments.Client_ID = clients.Client_ID
                    LEFT JOIN counselors on appointments.Counselor_ID = counselors.Counselor_ID
                    WHERE type = "Office";
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int aID = rs.getInt("Appointment ID");
                int coID = rs.getInt("Counselor ID");
                String coName = rs.getString("Counselor Name");
                int clID = rs.getInt("Client ID");
                String clName = rs.getString("Client Name");
                String description = rs.getString("Description");
                AppointmentType type =AppointmentType.valueOf(rs.getString("Type"));
                String start = rs.getString("Start Date and Time");
                String end = rs.getString("End Date and Time");
                String detail1 = rs.getString("Detail_1");
                String detail2 = rs.getString("Detail_2");


                    appointment = new OfficeAppointment(aID, coID, coName, clID, clName, type, description,
                            start, end, detail1, detail2);
                    allAppointments.add(appointment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TimeManipulation loop occurs here - need to write once bulk of rework completed - return converted list

        return allAppointments;
    }















    //Note - in controller, need to add clause when calling - if not Office, call default constructor
    public static int addnAppointment(Appointment a, Client cl, Counselor co) {
        int addConfirm = -1;

        try {
            String sql = """
                    INSERT INTO appointments (Appointment_ID, Type, Description, Start, End,\s
                    Detail_1, Detail_2, Counselor_ID, Client_ID, Create_Date,\s
                    Created_By, Last_Update, Last_Updated_By)
                    VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, a.getType().toString());
            ps.setString(2, a.getDescription());
            ps.setString(3,a.getStartTime());
            ps.setString(4,a.getEndTime());
            if(a instanceof OfficeAppointment) {
                Office office = new Office(co.getOfficeID());
                Suite suite = new Suite(co.getSuiteID());

                ps.setString(5,office.getBuildingName());
                ps.setString(6,suite.getSuiteName());
            } else if (a instanceof VirtualAppointment) {
                ps.setString(5, co.getCounselorUsername());
                ps.setString(6, cl.getClientUsername());
            } else if (a instanceof PhoneAppointment) {
                ps.setString(5, co.getCounselorPhone());
                ps.setString(6, cl.getClientPhone());
            }
            ps.setInt(7,co.getCounselorID());
            ps.setInt(8,cl.getClientID());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, Model.AuthorizedUser.getAuthorizedName());
            ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(12, Model.AuthorizedUser.getAuthorizedName());

            addConfirm = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addConfirm;
    }

    public static int editAppointment(Appointment a, Client cl, Counselor co) {
        int editConfirm = -1;
        try {
            String sql = """
                    UPDATE appointments SET\s
                    Type = ?,
                    Description = ?,
                    Start = ?,
                    End = ?,
                    Detail_1 = ?,
                    Detail_2 = ?,
                    Counselor_ID = ?,
                    Client_ID = ?,
                    Last_Update = ?,
                    Last_Updated_By = ?
                    where Appointment_ID = ?;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, a.getType().toString());
            ps.setString(2, a.getDescription());
            ps.setString(3,a.getStartTime());
            ps.setString(4,a.getEndTime());
            if(a instanceof OfficeAppointment) {
                Office office = new Office(co.getOfficeID());
                Suite suite = new Suite(co.getSuiteID());
                ps.setString(5,office.getBuildingName());
                ps.setString(6,suite.getSuiteName());
            } else if (a instanceof VirtualAppointment) {
                ps.setString(5, co.getCounselorUsername());
                ps.setString(6, cl.getClientUsername());
            } else if (a instanceof PhoneAppointment) {
                ps.setString(5, co.getCounselorPhone());
                ps.setString(6, cl.getClientPhone());
            }
            ps.setInt(7,co.getCounselorID());
            ps.setInt(8,cl.getClientID());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(11,a.getAppointmentID());
            editConfirm = ps.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editConfirm;
    }

    public static int deleteAppointment(Appointment a) {
        int deleteConfirm = -1;
        try {
            String sql = "DELETE FROM appointments WHERE appointment_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, a.getAppointmentID());
            deleteConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteConfirm;
    }
}
