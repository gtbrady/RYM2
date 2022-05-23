package DAO;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBnAppointment {

    public static ObservableList<nAppointment> getAppointments() {
        ObservableList<nAppointment> allAppointments = FXCollections.observableArrayList();
        //ObservableList<nAppointment> convertedAppointments = FXCollections.observableArrayList();
        nAppointment appointment;
        try {
            String sql = """
                    SELECT\s
                    appointments.appointment_ID as	"Appointment ID",
                    clients.client_name as "Client Name",
                    counselors.counselor_name as "Counselor Name",
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

        //TimeManipulation loop occurs here - need to write once bulk of rework completed - return converted list

        return allAppointments;
    }

}
