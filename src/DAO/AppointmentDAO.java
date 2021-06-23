package DAO;

import Model.Appointment;
import Model.Contact;
import Model.Schedule;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DB_connection;
import utils.DBquery;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class AppointmentDAO {

    public static void deleteAppointment(Appointment appointment) {

        try {

            Connection conn = DB_connection.getConnection();

            String SQLDeleteAppt = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement sda = DB_connection.getConnection().prepareStatement(SQLDeleteAppt);
            sda.setInt(1, appointment.getAppointmentID());
            sda.execute();

            System.out.println(" A(n) " + appointment.getType() + " was deleted from database");


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }


    }

    public static void modifyAppointment(Appointment appt) {

        Connection conn = DB_connection.getConnection();
        try {
            String SQLCreateCustomer = "UPDATE appointments SET Title = ?,  Description = ?, Location=?,Type=?, Start=? , End=?,  Last_Update= NOW(),  Last_Updated_By=?, Customer_ID=?,  User_ID=?, Contact_ID=? WHERE Appointment_ID = ?;";
            DBquery.setPreparedStatement(conn, SQLCreateCustomer);
            PreparedStatement ps = DBquery.getPreparedStatement();
            //key:value map method params to select statement


            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appt.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appt.getEnd()));
            ps.setInt(7, appt.getUserID());
            ps.setInt(8, appt.getCustomerID());
            ps.setInt(9, appt.getUserID());
            ps.setInt(10, appt.getContactID());
            ps.setInt(11, appt.getAppointmentID());


            ps.execute();
            System.out.println(ps);
            System.out.println("appointment updated");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static void addAppointment(int ID, String title, String desc, String location, Contact contact, String type, LocalDateTime start, LocalDateTime end, User user, int customerID) {

        Connection conn = DB_connection.getConnection();
        try {
            String SQLCreateCustomer = "INSERT INTO appointments (Appointment_ID, Title,  Description,  Location,Type, Start , End,  Create_Date,Created_By,  Last_Update,  Last_Updated_By, Customer_ID,  User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,now(),?,now(),?,?,?,?);";
            DBquery.setPreparedStatement(conn, SQLCreateCustomer);
            PreparedStatement ps = DBquery.getPreparedStatement();
            //key:value map method params to select statement


            ps.setInt(1, ID);
            ps.setString(2, title);
            ps.setString(3, desc);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, Timestamp.valueOf(start));
            ps.setTimestamp(7, Timestamp.valueOf(end));
            ps.setString(8, user.getUserName());
            ps.setString(9, user.getUserName());
            ps.setInt(10, customerID);
            ps.setInt(11, user.getUserID());
            ps.setInt(12, contact.getID());


            ps.execute();
            System.out.println(ps);
            System.out.println("New appointment added");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT * \n" +
                "FROM appointments\n" +
                "LEFT JOIN contacts\n" +
                "ON appointments.Contact_ID = contacts.Contact_ID;";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();

        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdated_By = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");


                //Calls Constructor to create new instance of appointment from DB Data
                Appointment AppointmentResult = new Appointment(
                        appointmentID,
                        title,
                        description,
                        location,
                        type,
                        start,
                        end,
                        createDate,
                        createdBy,
                        lastUpdate,
                        lastUpdated_By,
                        customerID,
                        userID,
                        contactID,
                        contactName,
                        email
                );
                // Add to list
                allAppointments.add(AppointmentResult);


            }

            DB_connection.closeConnection();
            return allAppointments;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

    public static ObservableList<Schedule> getCustomReportData() throws SQLException {

        ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
        //Establish Connection to DB
        Connection conn = DB_connection.getConnection();
        String SQLSelect = "SELECT monthname(DATE(start)) as month, type AS TYPE, count(type) as count FROM appointments group by type,month;";
        DBquery.setPreparedStatement(conn, SQLSelect);
        PreparedStatement ps = DBquery.getPreparedStatement();
        ps.execute();


        try {

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {


                String type = rs.getString("TYPE");
                String month = rs.getString("month");
                int count = rs.getInt("count");


                //Calls Constructor to create new instance of appointment from DB Data
                Schedule result = new Schedule(
                        type, month, count

                );
                // Add to list
                scheduleData.add(result);


            }

            DB_connection.closeConnection();
            return scheduleData;
        } catch (Exception e) {

            System.out.println("fail" + e.getMessage());

        }

        return null;


    }

}
