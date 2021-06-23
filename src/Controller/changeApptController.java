package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDao;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.InvalidTimeException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static utils.sharedFunctions.checkOverlap;

/**
 * Class is Controller for changing appointments.
 */

public class changeApptController {
    @FXML
    private TextField id_col;

    @FXML
    private TextField date_col;

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
    private ComboBox<Customer> customer_combo;

    @FXML
    private ComboBox<User> user_combo;

    @FXML
    private Button update_appt;

    @FXML
    private Appointment currentAppt;
    Stage stage;
    Parent scene;

    @FXML
    private void populateUser() throws SQLException {
        try {

            user_combo.setItems((UserDao.getAllUsers()));

        } catch (Exception e) {

            System.out.println(e.getMessage());


        }


    }

    @FXML
    private ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

    /**
     * This method receives data.
     */
    @FXML
    public void receiveData(Appointment appt) throws SQLException {


        id_col.setText(String.valueOf(appt.getAppointmentID()));
        date_col.setText(String.valueOf(appt.getCreateDate()));
        desc_col.setText(appt.getDescription());
        type_col.setText(appt.getType());
        title_col.setText(appt.getTitle());
        start_col.setText(String.valueOf(appt.getStart()));
        end_col.setText(String.valueOf(appt.getEnd()));
        //prepopulate combo boxes with previous selection
        contact_combo.setValue(ContactDAO.getContactFromID(appt.getContactID()));
        customer_combo.setValue(CustomerDAO.getCustomer(appt.getCustomerID()));
        currentAppt = appt;


    }

    /**
     * This method updates appt.
     */
    @FXML
    public void update_appt(ActionEvent e) throws IOException, SQLException {


        currentAppt.setAppointmentID(Integer.parseInt(id_col.getText()));
        currentAppt.setCreateDate(LocalDate.parse(date_col.getText()));
        currentAppt.setDescription(desc_col.getText());
        currentAppt.setType(type_col.getText());
        currentAppt.setTitle(title_col.getText());
        currentAppt.setStart(LocalDateTime.parse(start_col.getText()));
        currentAppt.setEnd(LocalDateTime.parse(end_col.getText()));
        currentAppt.setContactID(contact_combo.getSelectionModel().getSelectedItem().getID());
        currentAppt.setCustomerID(customer_combo.getSelectionModel().getSelectedItem().getID());
        currentAppt.setUserID(user_combo.getSelectionModel().getSelectedItem().getUserID());

        try {
            checkOverlap(currentAppt.getStart(),currentAppt.getAppointmentID());
            if (title_col.getText().isEmpty() || desc_col.getText().isEmpty() || type_col.getText().isEmpty()
                    || contact_combo.getValue() == null || start_col.getText().isEmpty() || end_col.getText().isEmpty()) {

                Alert missingFields = new Alert(Alert.AlertType.ERROR);
                missingFields.setContentText("Please fill out all fields");
                missingFields.showAndWait();

            } else {

                AppointmentDAO.modifyAppointment(currentAppt);
                System.out.println("appointment changed " + currentAppt.getAppointmentID());
                this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/Appointment_view.fxml"));
                this.stage.setScene(new Scene(this.scene));
                this.stage.setFullScreen(true);
                this.stage.show();

            }

        } catch (InvalidTimeException it) {
            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(it.getMessage());
            scheduleInterference.showAndWait();

        } catch (SQLException sql) {

            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(sql.getMessage());
            scheduleInterference.showAndWait();

        } catch (IOException io) {

            Alert scheduleInterference = new Alert(Alert.AlertType.ERROR);
            scheduleInterference.setContentText(io.getMessage());
            scheduleInterference.showAndWait();

        }


    }


    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() throws SQLException {
        populateUser();
        contact_combo.setItems(ContactDAO.getAllContacts());
        customer_combo.setItems(CustomerDAO.getAllCustomers());
    }
}
