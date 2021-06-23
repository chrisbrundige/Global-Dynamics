package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DB_connection;
import utils.DBquery;

import java.sql.*;

public class CountryDAO {

    public static Country getCountryFromDiv(Integer id) throws SQLException {

        Connection conn = DB_connection.getConnection();

        String SQLSelect = "SELECT  Country\n" +
                "FROM first_level_divisions\n" +
                "LEFT JOIN countries\n" +
                "ON first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                "WHERE Division_ID = ?;";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        //key:value map method params to select statement

        ps.setString(1, String.valueOf(id));
        ps.execute();
        Country selectedCountry = null;

        try {
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String name = rs.getString("Country");


                selectedCountry = new Country(name);


            }
            DB_connection.closeConnection();
            return selectedCountry;


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;
    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM countries;";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int ID = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Country CountryResult = new Country(ID, name, createDate, createdBy, lastUpdate, lastUpdatedBy);

                allCountries.add(CountryResult);


            }

            DB_connection.closeConnection();
            return allCountries;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

}
