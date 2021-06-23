package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import Model.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class is Controller for generating custom reports related to the primary application.
 */

public class reportController {
    Stage stage;
    Parent scene;

    @FXML
    private Button cust_records_hyperlink;

    @FXML
    private Button Appt_hyperlink;

    @FXML
    private Button reports_hyperlink;

    @FXML
    private TextArea custom_reports;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * method generates customer appointments.
     */
    @FXML
    void generateCustomerAppt() throws SQLException {

        scheduleList = AppointmentDAO.getCustomReportData();
        ObservableList<String> apptType = FXCollections.observableArrayList();


        custom_reports.appendText(" CUSTOMER APPOINTMENTS BY MONTH / TYPE " + "\n");
       scheduleList.forEach((x)->custom_reports.appendText(x.getMonth()+" "+x.getType()+" "+x.getNumber() +"\n"));




      //  appointmentList.forEach((a) -> {
       //     custom_reports.appendText(String.valueOf(a.getStart().getMonth()) + " " + a.getType() + "\n");
     //   });




    }

    /**
     * method generates customer schedule.  this method contains a lambda expression that iterates trough a list and adds each item to a custom report.  The use of a lambda
     * in this context greatly reduces the complexity and increases the readability of the code compared to defined functions.
     */
    @FXML
    void generateSchedule() throws SQLException {

        custom_reports.appendText("SCHEDULE" + "\n");
        appointmentList = AppointmentDAO.getAllAppointments();
        appointmentList.forEach((appt) -> {
            custom_reports.appendText(appt.getContactName() + " , " + "ID:" + appt.getAppointmentID() + " ,Type " + appt.getType() + " , TITLE: " + appt.getTitle() + " ,DESC: " + appt.getDescription() + " ,START " + appt.getStart() + " ,END " + appt.getEnd() + "." + "\n");

        });


    }

    /**
     * method generates new customer.
     */
    @FXML
    void generateNewCustomers() throws SQLException {

        customerList = CustomerDAO.getAllCustomers();
        custom_reports.appendText(" New Customers in last 90 days:  " + "\n");

        if (customerList.isEmpty()) {

            custom_reports.appendText("NO NEW CUSTOMERS NOTED " + "\n");

        } else {
            customerList.forEach((customer) -> {

                if (customer.getCreateDate().toLocalDate().isBefore(LocalDate.now().minusDays(90)) || customer.getCreateDate().toLocalDate().equals(LocalDate.now())) {

                    custom_reports.appendText(customer.getName() + " : " + customer.getCreateDate() + "\n");

                }


            });
        }

    }

    /**
     * method navigates to customer records.
     */
    @FXML
    void goto_customer_records(ActionEvent event) throws Exception {

        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/customer_record_view.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.setFullScreen(true);
        this.stage.show();

    }

    /**
     * method navigates to appointment scene.
     */
    @FXML
    void goto_appt_scene(ActionEvent event) throws Exception {

        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/Appointment_view.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.setFullScreen(true);
        this.stage.show();

    }

    /**
     * method navigates to reports.
     */
    @FXML
    void goto_reports(ActionEvent event) throws Exception {

        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/reports.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.setFullScreen(true);
        this.stage.show();

    }

    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() throws SQLException {

        generateCustomerAppt();
        generateNewCustomers();
        generateSchedule();

    }

}