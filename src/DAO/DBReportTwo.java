package DAO;

import Model.AppointmentType;
import Model.Counselor;
import Model.ReportTwo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBReportTwo {

    public static ObservableList<ReportTwo> getReportTwo(Counselor c) {
        ObservableList<ReportTwo> reportTwo = FXCollections.observableArrayList();
        //ObservableList<ReportTwo> reportTwoConverted = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT counselors.counselor_name as "Counselor Name",\s
                    appointments.Description,
                    appointments.Type,
                    clients.client_name as "Client Name",
                    appointments.Start,
                    appointments.End\s
                    from appointments
                    LEFT JOIN counselors on appointments.Counselor_ID = counselors.Counselor_ID
                    LEFT JOIN clients on appointments.Client_ID = clients.Client_ID
                    WHERE appointments.counselor_ID = ?
                    ORDER BY Start, End, clients.Client_Name;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, c.getCounselorID());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String coName = rs.getString("Counselor Name");
                String description = rs.getString("Description");
                AppointmentType type = AppointmentType.valueOf(rs.getString("Type"));
                String clName = rs.getString("Client Name");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                ReportTwo two = new ReportTwo(coName,description,type,clName,start,end);
                reportTwo.add(two);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return reportTwo;
    }
}
