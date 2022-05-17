package DAO;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves all first-level-divisions from the first-level-division table
 */
public class DBFirstDivision {

    /**
     * Retrieves all first-level-divisions
     * @return An observable list of all first-level-divisions in the first-level-division table
     */
    public static ObservableList<FirstLevelDivision> getFirstLevel() {
        ObservableList<FirstLevelDivision> levelList = FXCollections.observableArrayList();
        try {
            String sql = "select \n" +
                    "Division_ID,\n" +
                    "Division,\n" +
                    "Country_ID\n" +
                    "from first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divID = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                int cId = rs.getInt("Country_ID");
                FirstLevelDivision f = new FirstLevelDivision(divID, div, cId );
                levelList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return levelList;
    }
}
