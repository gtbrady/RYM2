package DAO;

import Model.RepThree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves the total number of customers by country. Represented as a repThree object
 * Retrieves data from the customers, first_level_divisions and countries tables
 */
public class DBRepThree {

    /**
     * Retrieves all repThree objects
     * @return An observable list of repThree objects
     */
    public static ObservableList<RepThree> getRepThree() {
        ObservableList<RepThree> repThree = FXCollections.observableArrayList();
        try {
            String sql = """
                    select Country,\s
                    count(customer_name) as CUSTOMER_COUNT\s
                    from customers\s
                    inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID
                    inner join countries on countries.Country_ID = first_level_divisions.Country_ID
                    group by Country""";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String c = rs.getString("Country");
                int ct = rs.getInt("CUSTOMER_COUNT");
                RepThree three = new RepThree(c, ct);
                repThree.add(three);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return repThree;
    }
}
