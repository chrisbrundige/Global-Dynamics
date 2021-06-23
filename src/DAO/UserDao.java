package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DB_connection;
import utils.DBquery;

import java.sql.*;

public class UserDao {

    public static User getUser(String un) throws SQLException {

        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        // SELECT User by user name
        String SQLselect = " SELECT * FROM users WHERE User_Name= ? ";
        //create a prepared statement
        DBquery.setPreparedStatement(conn, SQLselect);
        PreparedStatement ps = DBquery.getPreparedStatement();

        //map function parameter username to ? in prepared statement
        ps.setString(1, un);
        //Send prepared statement to DB

        try {
            ps.execute();
            // the results of the query to iterate through
            //
            User userResult;
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                Integer userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                userResult = new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                return userResult;

            }
            DB_connection.closeConnection();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    //get all users method
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * FROM users";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {



            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                Integer userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");


                User userResult = new User(userID, userName, password);
                allUsers.add(userResult);



            }

            DB_connection.closeConnection();

            return allUsers;
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;


    }

}
