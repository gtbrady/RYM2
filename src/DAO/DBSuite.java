package DAO;

import Model.Suite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSuite {

    public static ObservableList<Suite> getSuites() {
        ObservableList<Suite> suiteList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT\s
                    Suite_ID,
                    Office_ID,
                    Suite_Name
                    FROM suites
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int suiteID = rs.getInt("Suite_ID");
                int officeID = rs.getInt("Office_ID");
                String name = rs.getString("Suite_Name");
                Suite s = new Suite(suiteID,officeID,name);
                suiteList.add(s);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return suiteList;
    }
}
