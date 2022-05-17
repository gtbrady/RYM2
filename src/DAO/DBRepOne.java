package DAO;


import Model.RepOne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves the total number of appointments by type and month. Represented by
 * a repOne object. Retrieves data from the appointments table
 */
public class DBRepOne {


    /**
     * Retrieves all repOne objects
     * @return An observable list of repOne objects
     */
    public static ObservableList<RepOne> getReportOne () {
        ObservableList<RepOne> repOne = FXCollections.observableArrayList();
        try {
            String sql = """
                    select monthname(start) as Month,\s
                    type as Type,count(type) as TOTAL_APPOINTMENTS
                    from appointments group by monthname(start), type
                    """;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int totalAppointments = rs.getInt("TOTAL_APPOINTMENTS");
                RepOne one = new RepOne(month, type, totalAppointments);
                repOne.add(one);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return repOne;
    }
}
