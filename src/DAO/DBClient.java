package DAO;

import Model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBClient {

    public static ObservableList<Client> getClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        try {
            String sql = """
                select client_ID as "Client ID", client_name as "Name",client_username as "Username",
                client_email as "Email", client_phone as "Phone Number"
                FROM clients order by client_id ASC;
                    """;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int clientID = rs.getInt("Client ID");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone Number");
                String username = rs.getString("Username");
                String email = rs.getString("Email");
                Client c = new Client(clientID, name, phone,username, email);
                clientList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    public static int addClient(Client c) {
        int addConfirm = -1;
        try {
            String sql = """
                    INSERT INTO clients (Client_ID, Client_Name, Client_Username, Client_Email,\s
                    Client_Phone, Create_Date, Created_By, Last_Update, Last_Updated_By)\s
                    VALUES
                    (NULL, ?, ?, ?,	?,	?,	?, ?, ?);
                    
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,c.getClientName());
            ps.setString(2,c.getClientUsername());
            ps.setString(3, c.getClientEmail());
            ps.setString(4, c.getClientPhone());
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

    public static int editClient(Client c) {
        int updateConfirm =-1;
        try {
            String sql = """
                    UPDATE clients SET
                    
                    Client_Name = ?,
                    Client_Username = ?,
                    Client_Email = ?,
                    Client_Phone = ?,
                    Last_Update = ?,
                    Last_Updated_By = ?
                    
                    WHERE Client_ID = ?;
                                        
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, c.getClientName());
            ps.setString(2, c.getClientUsername());
            ps.setString(3,c.getClientEmail());
            ps.setString(4,c.getClientPhone());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6,Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(7,c.getClientID());
            updateConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateConfirm;
    }

    public static int deleteClient(Client c) {
        int deleteConfirm = -1;
        try {
            String sql = "delete from clients where client_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,c.getClientID());
            deleteConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteConfirm;
    }















}
