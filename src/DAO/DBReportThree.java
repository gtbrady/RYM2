package DAO;

import Model.ReportThree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBReportThree {

    public static ObservableList<ReportThree> getReportThree() {
        ObservableList<ReportThree> reportThree = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT\s
                    detail_1 AS "Building",
                    count(appointment_id) AS "APPOINTMENT_COUNT"
                    FROM appointments\s
                    WHERE type = "Office"
                    GROUP BY detail_1;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String name = rs.getString("Building");
                int count = rs.getInt("APPOINTMENT_COUNT");
                ReportThree three = new ReportThree(name,count);
                reportThree.add(three);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return reportThree;
    }
}
