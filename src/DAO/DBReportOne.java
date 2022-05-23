package DAO;

import Model.AppointmentType;
import Model.ReportOne;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBReportOne {
    public static ObservableList<ReportOne> getReportOne() {
        ObservableList<ReportOne> reportOne = FXCollections.observableArrayList();
        try {
            String sql = """
                    select monthname(start) as Month,
                    type as Type,count(type) as TOTAL_APPOINTMENTS
                    from appointments group by monthname(start), type
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int totalAppointments = rs.getInt("TOTAL_APPOINTMENTS");
                ReportOne one = new ReportOne(month, AppointmentType.valueOf(type), totalAppointments);
                reportOne.add(one);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportOne;
    }
}
