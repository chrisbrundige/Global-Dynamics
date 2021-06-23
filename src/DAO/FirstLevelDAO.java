package DAO;

import Model.Customer;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import utils.DB_connection;
import utils.DBquery;

import java.sql.*;

public class FirstLevelDAO {


    public static FirstLevel getFirstLevelByCustomer(Customer customer) throws SQLException {
        String SQLSelect = "SELECT * FROM first_level_divisions WHERE Division_ID = ?; ";
        PreparedStatement ps = DB_connection.getConnection().prepareStatement(SQLSelect);
        ps.setInt(1, customer.getDivision());
        ps.execute();
        FirstLevel divResult = null;


        try {


            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");

                divResult = new FirstLevel(ID, div, createDate, createdBy, lastUpdate, lastUpdatedBy, division);


            }

            DB_connection.closeConnection();

            return divResult;

        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

    public static ObservableList<FirstLevel> getAllFirstLevelData() throws SQLException {
        ObservableList<FirstLevel> firstLevelData = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM first_level_divisions";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");

                FirstLevel divResult = new FirstLevel(ID, div, createDate, createdBy, lastUpdate, lastUpdatedBy, division);
                firstLevelData.add(divResult);


            }

            DB_connection.closeConnection();
            return firstLevelData;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }



    public static ObservableList<FirstLevel> getDivByCountry(Integer country) throws SQLException {
        ObservableList<FirstLevel> firstLevelData = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?; ";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.setInt(1, country);
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");

                FirstLevel divResult = new FirstLevel(ID, div, createDate, createdBy, lastUpdate, lastUpdatedBy, division);
                firstLevelData.add(divResult);


            }

            DB_connection.closeConnection();
            return firstLevelData;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }


}
