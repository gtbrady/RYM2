package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves country data from the countries table
 */
public class DBCountry {

    /**
     * Retrieves all countries
     * @return An observable list of all countries in the countries table
     */
    public static ObservableList<Country> getCountry() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try {
            String sql = "select \n" +
                    "country_id,\n" +
                    "country\n" +
                    "from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("country_id");
                String country = rs.getString("country");
                Country c = new Country(countryID, country);
                countryList.add(c);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }
}
