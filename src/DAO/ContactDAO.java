package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DB_connection;
import utils.DBquery;

import java.sql.*;

public class ContactDAO {

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM contacts";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");


                Contact ContactResult = new Contact(ID, name, email);

                allContacts.add(ContactResult);


            }

            DB_connection.closeConnection();
            return allContacts;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

    public static Contact getContactFromID(int ID) throws SQLException {

        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM contacts WHERE Contact_ID = ?;";

        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.setInt(1, ID);
        ps.execute();
        Contact ContactResult = null;


        ResultSet rs = ps.getResultSet();
        while (rs.next()) {

            int contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");


            ContactResult = new Contact(contactID, name, email);


        }


        return ContactResult;


    }


}
