package DAO;

import Model.Office;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOffice {

    public static ObservableList<Office> getOffices() {
        ObservableList<Office> officeList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT 
                    Office_ID,
                    Building_Name,
                    Building_Address,
                    City,
                    State,
                    Zip_Code 
                    FROM offices                                        
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int officeID = rs.getInt("Office_ID");
                String name = rs.getString("Building_Name");
                String address = rs.getString("Building_Address");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zip = rs.getString("Zip_Code");
                Office o = new Office(officeID, name, address, city, state, zip);
                officeList.add(o);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return officeList;
    }

    public static ObservableList<String> getOfficeNames() {
        ObservableList<String> officeNameList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT\s
                    Office_ID,
                    Building_Name,
                    Building_Address,
                    City,
                    State,
                    Zip_Code\s
                    FROM offices                                        
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Building_Name");
                officeNameList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return officeNameList;

    }

}
