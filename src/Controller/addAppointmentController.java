package Controller;

import DAO.*;
import Model.Contact;
import Model.Country;
import Model.Customer;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.InvalidTimeException;
import utils.sharedFunctions;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static utils.sharedFunctions.checkOverlap;


/**
 * Class is Controller for adding Appointments.
 */

public class addAppointmentController {


    Stage stage;
    Parent scene;
    @FXML
    ComboBox<Country> location_combo;
    @FXML
    private TextField ID_col;
    @FXML
    private DatePicker date_col;

    @FXML
    private TextField desc_col;

    @FXML
    private TextField type_col;

    @FXML
    private TextField title_col;

    @FXML
    private ComboBox<Contact> contact_combo;

    @FXML
    private TextField start_col;

    @FXML
    private TextField end_col;

    @FXML
    private ComboBox<Customer> customerId_combo;

    @FXML
    private Button add_appt_button;

    @FXML
    private ComboBox<User> user_combo;


    /**
     * Method adds appointments to list.
     * this method contains a lambda expression that iterates through all appointments and compares the values to ensure duplicate appointments are not added.
     * the use of a lambda expression allows for a simpler expression of the intent of the code. This lambda replaces a for loop and allows each appointment time in the list to be compared to
     * other existing times
     */
    @FXML
    void add_appt(ActionEvent event) throws SQLException, InvalidTimeException {


        //allows for date time to be separate combo boxes
        LocalDate date = date_col.getValue();
        LocalTime startTime = LocalTime.parse(start_col.getText());
        LocalTime endTime = LocalTime.parse(end_col.getText());

        int ID = sharedFunctions.generateRand();
        String title = title_col.getText();
        String desc = desc_col.getText();
        String location = location_combo.getValue().getCountry();
        Contact contact = contact_combo.getValue();
        String type = type_col.getText();
        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);
        int customerID = customerId_combo.getValue().getID();
        User user = user_combo.getValue();

        // error handle appt overlap
        try {
            checkOverlap(start);
            if (title_col.getText().isEmpty() || desc_col.getText().isEmpty() || type_col.getText().isEmpty() || location_combo.getValue() == null
                    || contact_combo.getValue() == null || start_col.getText().isEmpty() || end_col.getText().isEmpty() ||
                    user_combo.getValue() == null) {


                Alert missingFields = new Alert(Alert.AlertType.ERROR);
                missingFields.setContentText("Please fill out all fields");
                missingFields.showAndWait();


            } else {

                AppointmentDAO.addAppointment(ID, title, desc, location, contact, type, start, end, user, customerID);

                this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                this.scene = FXMLLoader.load(this.getClass().getResource("/Views/Appointment_view.fxml"));
                this.stage.setScene(new Scene(this.scene));
                this.stage.setFullScreen(true);
                this.stage.show();


            }

        } catch (InvalidTimeException e) {
            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(e.getMessage());
            scheduleInterference.showAndWait();

        } catch( SQLException sql){

            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(sql.getMessage());
            scheduleInterference.showAndWait();

        }catch(IOException io){

            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(io.getMessage());
            scheduleInterference.showAndWait();

        }


    }

    /**
     * Method populates first-level div combo boxes.
     */
    private void populateCountry() {
        try {
            location_combo.setItems(CountryDAO.getAllCountries());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method populates first-level div combo boxes.
     */
    private void populateContact() {
        try {
            contact_combo.setItems(ContactDAO.getAllContacts());
            System.out.println(contact_combo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method populates first-level div combo boxes.
     */
    private void populateCustomer() {
        try {
            customerId_combo.setItems(CustomerDAO.getAllCustomers());
        } catch (Exception e) {

        }
    }

    private void populateUser() throws SQLException {
        try {

            user_combo.setItems((UserDao.getAllUsers()));

        } catch (Exception e) {

            System.out.println(e.getMessage());


        }


    }

    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() throws SQLException {
        populateCountry();
        populateContact();
        populateCustomer();
        populateUser();


    }


}
