package DAO;


import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Retrieves customer data from the customers table
 */
public class DBCustomer {

    /**
     * Retrieves all customers
     * @return An observable list of all customers in the customers table
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {

            String sql = "select customers.Customer_ID as 'Customer ID', \n" +
                    "customers.Customer_Name as 'Customer Name', \n" +
                    "customers.Phone as 'Phone Number',\n" +
                    "customers.Address as 'Street Address', \n" +
                    "customers.Postal_Code as 'Postal Code',\n" +
                    "first_level_divisions.Division as 'First Level',\n" +
                    "first_level_divisions.Division_ID as 'First Level ID',\n" +
                    "countries.Country,\n" +
                    "countries.Country_ID as 'Country ID'\n" +
                    "\n" +
                    "from customers \n" +
                    "inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "inner join countries on first_level_divisions.Country_ID = countries.Country_ID order by Customer_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer ID");
                String custName = rs.getString("Customer Name");
                String phoneNumber = rs.getString("Phone Number");
                String address = rs.getString("Street Address");
                String postalCode = rs.getString("Postal Code");
                String firstLevel = rs.getString("First Level");
                int flId = rs.getInt("First Level ID");
                String country = rs.getString("Country");
                int cId = rs.getInt("Country ID");
                Customer c = new Customer(custID,custName,phoneNumber, address,postalCode,firstLevel,flId, country, cId);
                customerList.add(c);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * Inserts the provided customer into the customers table
     * @param c The customer provided by the user
     * @return The number of rows inserted
     */
    public static int addCustomer(Customer c) {
        int addConfirm = -1;
        try {
            String sql = """
                    insert into customers
                    (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)
                    values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,c.getCustomerName());
            ps.setString(2,c.getAddress());
            ps.setString(3, c.getPostalCode());
            ps.setString(4,c.getPhoneNumber());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, Model.AuthorizedUser.getAuthorizedName());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(9,c.getFirstLevelId());

            addConfirm = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return addConfirm;

    }

    /**
     * Updates the provided customer in the customers table
     * @param c The customer provided by the user
     * @return The number of rows updated
     */
    public static int editCustomer(Customer c) {
        int updateConfirm =-1;

        try{
            String sql = """
                    UPDATE customers SET
                    Customer_Name = ?,
                    Address = ?,
                    Postal_Code = ?,
                    Phone = ?,
                    Last_Update = ?,
                    Last_Updated_By = ?,
                    Division_ID = ?
                    where customer_id = ?;
                    """;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,c.getCustomerName());
            ps.setString(2,c.getAddress());
            ps.setString(3, c.getPostalCode());
            ps.setString(4,c.getPhoneNumber());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, Model.AuthorizedUser.getAuthorizedName());
            ps.setInt(7,c.getFirstLevelId());
            ps.setInt(8,c.getCustomerID());
            updateConfirm = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return updateConfirm;
    }

    /**
     * Deletes the provided customer in the customers table
     * @param c The customer provided by the user
     * @return The number of rows deleted
     */
    public static int deleteCustomer(Customer c) {
        int deleteConfirm = -1;
        try {
            String sql = "delete from customers where customer_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,c.getCustomerID());
            deleteConfirm = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteConfirm;
    }
}
