package DAO;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.DB_connection;
import utils.DBquery;

import java.sql.*;

public class CustomerDAO {

    public static void addCustomer(int ID, String name, String address, String postalCode, String phone, String createdBy, String lastUpdatedBy, int division) throws SQLException {

        Connection conn = DB_connection.getConnection();

        String SQLCreateCustomer = "INSERT INTO customers(Customer_ID ,Customer_Name, Address,Postal_Code , Phone ,Create_Date,  Created_By ,Last_Updated_By, Division_ID ) VALUES(?,?,?,?,?,current_timestamp(),?,?,?);";
        DBquery.setPreparedStatement(conn, SQLCreateCustomer);
        PreparedStatement ps = DBquery.getPreparedStatement();
        //key:value map method params to select statement

        try {
            ps.setInt(1, ID);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setString(6, createdBy);
            ps.setString(7, lastUpdatedBy);
            ps.setInt(8, division);
            ps.execute();

            System.out.println("New Customer added");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    public static Customer getCustomer(Integer id) throws SQLException {

        Connection conn = DB_connection.getConnection();

        String SQLSelect = "SELECT * FROM customers WHERE Customer_ID = ? ";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        //key:value map method params to select statement

        ps.setString(1, String.valueOf(id));
        ps.execute();
        Customer selectedCustomer = null;

        try {
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");
                selectedCustomer = new Customer(ID, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, division);


            }
            DB_connection.closeConnection();
            return selectedCustomer;


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
            return null;

    }

    public static void updateCustomer(Customer customer) throws SQLException {
        try {


            String SQLUpdate = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = DB_connection.getConnection().prepareStatement(SQLUpdate);
            DBquery.setPreparedStatement(DB_connection.getConnection(), SQLUpdate);
            // set prepared statement
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivision());
            ps.setInt(6, customer.getID());

            ps.execute();


        } catch (Exception e) {
            e.getMessage();
        }


    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM customers";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");

                Customer customerResult = new Customer(ID, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, division);

                allCustomers.add(customerResult);


            }

            DB_connection.closeConnection();
            return allCustomers;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

    public static void deleteCustomer(Customer customer) throws SQLException {

        try {


            String SQLDeleteAppt = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement sda = DB_connection.getConnection().prepareStatement(SQLDeleteAppt);
            sda.setInt(1, customer.getID());
            sda.execute();


            String SQLDelete = "DELETE FROM customers WHERE Customer_ID = ?;";
            PreparedStatement SQLDeleteCust = DB_connection.getConnection().prepareStatement(SQLDelete);
            SQLDeleteCust.setInt(1, customer.getID());
            SQLDeleteCust.execute();


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }


    }


}
