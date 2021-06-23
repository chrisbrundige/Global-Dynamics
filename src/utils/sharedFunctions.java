package utils;


import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.time.*;
import java.util.Random;

public class sharedFunctions {
    /**
     * Set message method sets label message.
     *
     * @param label
     * @param msg
     */
    public static void setMessage(Label label, String msg) {

        label.setText(msg);


    }


    public static void checkOverlap(LocalDateTime start, int apptID) throws SQLException, InvalidTimeException {

        /** check for overlapping appointmentTimes */
        try {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            appointmentList = AppointmentDAO.getAllAppointments();
            for (Appointment appt : appointmentList) {//get the start time in eastern time
                ZonedDateTime newAppt = appt.getStart().atZone(ZoneId.systemDefault());
                ZonedDateTime newEastern = newAppt.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalTime newInEastern = newEastern.toLocalTime();
                LocalDate newInEasternDate = newEastern.toLocalDate();

                if (apptID != appt.getAppointmentID()) {

                    if (start.toLocalDate().equals(newInEasternDate) && ((start.isAfter(appt.getStart()) && start.isBefore(appt.getEnd())) || start.equals(appt.getStart()))) {


                        throw new InvalidTimeException("Appointment hours overlap, please choose a different time");


                    }
                }


            }

        } catch (SQLException e) {
            System.out.println("Error Connecting to SQL database");
        }


    }


    public static void checkOverlap(LocalDateTime start) throws SQLException, InvalidTimeException {

        /** check for overlapping appointmentTimes */
        try {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            appointmentList = AppointmentDAO.getAllAppointments();
            for (Appointment appt : appointmentList) {//get the start time in eastern time
                ZonedDateTime newAppt = appt.getStart().atZone(ZoneId.systemDefault());
                ZonedDateTime newEastern = newAppt.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalTime newInEastern = newEastern.toLocalTime();
                LocalDate newInEasternDate = newEastern.toLocalDate();


                if (start.toLocalDate().equals(newInEasternDate) && ((start.isAfter(appt.getStart()) && start.isBefore(appt.getEnd())) || start.equals(appt.getStart()))) {


                    throw new InvalidTimeException("Appointment hours overlap, please choose a different time");


                }


            }

        } catch (SQLException e) {
            System.out.println("Error Connecting to SQL database");
        }


    }


    public static void checkStart(Appointment currentAppt) {

        /** check start time against EST*/
        //start with local date time
        //Convert to a zonedDatetime at the origin zoneID

        ZonedDateTime origin = currentAppt.getStart().atZone(ZoneId.systemDefault());
        //convert that to a zoneddatetime at the target zone id with zone same instant
        ZonedDateTime eastern = origin.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime originInEastern = eastern.toLocalDateTime();
        LocalTime easternHours = originInEastern.toLocalTime();
        System.out.println(originInEastern);

        // business hours for office in eastern time
        LocalTime busStart = LocalTime.of(8, 0);
        LocalTime busend = LocalTime.of(22, 0);


        if (easternHours.isAfter(busend) || easternHours.isBefore(busStart)) {
            Alert afterHours = new Alert(Alert.AlertType.ERROR);
            afterHours.setContentText("Appointment time is after hours, Please select a time before 10pm EST OR after 8 AM EST ");
            afterHours.showAndWait();
            System.out.println("appointment time is after hours");
        }


    }


    /**
     * function generates a random number
     */
    public static Integer generateRand() {
        Random rand = new Random();

        Integer seed = Integer.MAX_VALUE;
        int randInt = rand.nextInt(seed);
        System.out.println(randInt);
        return randInt;


    }


}