package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

import static utils.sharedFunctions.setMessage;

/**
 * Class is Controller for Viewing appointments in a table view.
 */

public class ApptViewController {
    Stage stage;
    Parent scene;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    RadioButton selectedToggle = null;

    @FXML
    private Button cust_records_hyperlink;

    @FXML
    private Button Appt_hyperlink;

    @FXML
    private Label appt_reminder;

    @FXML
    private Button reports_hyperlink;

    @FXML
    private Button Add_appt_button;

    @FXML
    private RadioButton week_view_radio;

    @FXML
    private RadioButton month_view_radio;

    @FXML
    private RadioButton all_view_radio;

    @FXML
    private TableView<Appointment> appointment_table;

    @FXML
    private TableColumn<Appointment, Integer> id_col;

    @FXML
    private TableColumn<Appointment, String> title_col;

    @FXML
    private TableColumn<Appointment, String> desc_col;

    @FXML
    private TableColumn<Appointment, String> location_col;

    @FXML
    private TableColumn<Appointment, String> contact_col;

    @FXML
    private TableColumn<Appointment, String> email_col;

    @FXML
    private TableColumn<Appointment, String> type_col;

    @FXML
    private TableColumn<Appointment, Date> start_col;

    @FXML
    private TableColumn<Appointment, Date> end_col;

    @FXML
    private TableColumn<Appointment, Integer> customerID_col;

    @FXML
    private Button update_appt;

    @FXML
    private Button del_appt;

    @FXML
    /** This method updates user appointments . */
    void updateAppt(ActionEvent e) {


        try {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/changeAppt.fxml"));
            loader.load();

            changeApptController apptController = loader.getController();
            apptController.receiveData(appointment_table.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait();
        } catch (Exception exc) {

            System.out.println(exc.getMessage());
        }


    }

    /**
     * This method deletes appointments.
     */
    @FXML

    private void deleteAppt() throws SQLException, Exception {

        Appointment deleteMe = appointment_table.getSelectionModel().getSelectedItem();
        System.out.println(deleteMe.getAppointmentID());

        AppointmentDAO.deleteAppointment(deleteMe);
        int name = deleteMe.getAppointmentID();
        //creates a custom Alert that dynamically populates from customer class object
        Alert custDeleted = new Alert(Alert.AlertType.INFORMATION);
        custDeleted.setTitle("APPOINTMENT DELETED");

        custDeleted.setContentText(" An Appointment of \" " + deleteMe.getType() + "\" with id " + deleteMe.getAppointmentID() + " was deleted from database");
        custDeleted.show();
        populateAppointmentTable();

    }

    /**
     * This method checks to see which box is selected.
     */
    @FXML
    private void checkSelection() {

    }

    /**
     * This method filters appointments based on week.
     */
    @FXML
    public void filterWeek() throws SQLException {


        FilteredList<Appointment> weekly = new FilteredList<>(AppointmentDAO.getAllAppointments(), p -> true);
        appointment_table.setItems(weekly);

        weekly.setPredicate((appt) -> {
            if (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now().plusDays(7))) {
                return true;
            } else {
                return false;
            }

        });


    }

    /**
     * This method filters appointments based on month.
     */
    @FXML
    void filterMonth() throws SQLException {

        FilteredList<Appointment> monthly = new FilteredList<>(AppointmentDAO.getAllAppointments(), p -> true);
        appointment_table.setItems(monthly);

        monthly.setPredicate((appt) -> {
            if (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now().plusDays(31))) {
                return true;
            } else {
                return false;
            }

        });


    }

    /**
     * This method navigates add appt.
     */
    @FXML
    void goto_add_appt(ActionEvent event) throws Exception {
        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/add_appointment_view.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.setFullScreen(true);
        this.stage.show();

    }

    /**
     * This method navigates to appt scene.
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
     * This method navigates to customer records.
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
     * This method navigates to reports.
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
     * This method populates the appointment table.
     */
    @FXML
    private void populateAppointmentTable() {
        try {
            id_col.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
            location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact_col.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            start_col.setCellValueFactory(new PropertyValueFactory<>("start"));
            end_col.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerID_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appointmentList = AppointmentDAO.getAllAppointments();
            appointment_table.setItems(appointmentList);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method checks if an appointment is scheduled in the nxt 15 min .
     */

    public void checkNextmeeting() {

        //get appointments
        appointmentList.forEach(appt -> {
            if (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                setMessage(appt_reminder, "NEXT APPOINTMENT " + appt.getAppointmentID() + " Scheduled at : "
                        + appt.getStart().getHour() + ":" + appt.getStart().getMinute());
            }
        });

    }

    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() {
        //call to get all appointments
        populateAppointmentTable();
        //group radio buttons
        ToggleGroup viewSelect = new ToggleGroup();
        week_view_radio.setToggleGroup(viewSelect);
        week_view_radio.setUserData("week");
        month_view_radio.setToggleGroup(viewSelect);
        month_view_radio.setUserData("month");
        all_view_radio.setToggleGroup(viewSelect);
        all_view_radio.setUserData("all");
        all_view_radio.setSelected(true);
        //change listener
        viewSelect.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (viewSelect.getSelectedToggle() != null) {

                    String usrSelect = viewSelect.getSelectedToggle().getUserData().toString();
                    if (usrSelect == "week") {
                        try {
                            filterWeek();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else if (usrSelect == "month") {
                        try {
                            filterMonth();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    } else {
                        appointment_table.setItems(appointmentList);
                    }

                }
            }
        });

        //set default message
        setMessage(appt_reminder, "No Appointments scheduled");
        checkNextmeeting();

    }


}
