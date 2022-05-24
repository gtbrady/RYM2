package DAO;

import Model.Counselor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBCounselor {

    public static ObservableList<Counselor> getCounselors() {
        ObservableList<Counselor> counselorList = FXCollections.observableArrayList();
        try {
            String sql = """
                    SELECT counselor_ID as "Counselor ID",
                    counselor_name as "Name",
                    counselor_username as "Username",
                    counselor_email as "Email",
                    counselor_phone as "Phone Number"
                    FROM counselors order by counselor_id asc;
                    """;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int counselorID = rs.getInt("counselor ID");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone Number");
                String username = rs.getString("Username");
                String email = rs.getString("Email");
                Counselor c = new Counselor(counselorID, name, phone,username, email);
                counselorList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counselorList;
    }

    public static int addCounselor(Counselor c) {
        int addConfirm = -1;
        try {
            String sql = """                
                    INSERT INTO counselors (counselor_ID,
                    counselor_Name, counselor_Username, counselor_Email,
                    counselor_Phone, Create_Date, Created_By, Last_Update,
                    Last_Updated_By)
                    VALUES
                    (NULL,?,?,?,?,?,?,?,?);
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,c.getCounselorName());
            ps.setString(2, c.getCounselorPhone());
            ps.setString(3,c.getCounselorUsername());
            ps.setString(4, c.getCounselorEmail());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, Model.AuthorizedUser.getAuthorizedName());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8,Model.AuthorizedUser.getAuthorizedName());
            addConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            }
        return addConfirm;
    }

    public static int editCounselor(Counselor c) {
        int updateConfirm =-1;
        try {
            String sql = """
                    UPDATE counselors SET
                    counselor_Name = ?,
                    counselor_Username = ?,
                    counselor_Email = ?,
                    counselor_Phone = ?,
                    Last_Update = ?,
                    Last_Updated_By = ?
                    WHERE counselor_ID = ?;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, c.getCounselorName());
            ps.setString(2, c.getCounselorUsername());
            ps.setString(3,c.getCounselorEmail());
            ps.setString(4,c.getCounselorPhone());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6,Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(7,c.getCounselorID());
            updateConfirm = ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
        }    return updateConfirm;
    }

    public static int deleteCounselor(Counselor c) {
        int deleteConfirm = -1;
        try {
            String sql = "delete from counselors where counselor_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,c.getCounselorID());
            deleteConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteConfirm;}
}
