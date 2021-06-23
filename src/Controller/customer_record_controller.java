package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static utils.sharedFunctions.setMessage;

/**
 * Class is Controller for viewing all customers in a table view.
 */


public class customer_record_controller {
    Stage stage;
    Parent scene;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> customer_table_view;

    @FXML
    private TableColumn<Customer, Integer> id_column;

    @FXML
    private TableColumn<Customer, String> name_column;

    @FXML
    private TableColumn<Customer, String> address_column;

    @FXML
    private TableColumn<Customer, String> postal_code_column;

    @FXML
    private TableColumn<Customer, String> phone_column;


    @FXML
    private TableColumn<Customer, Date> create_column;

    @FXML
    private TableColumn<Customer, String> createBy_col;

    @FXML
    private TableColumn<Customer, LocalDateTime> update_col;

    @FXML
    private TableColumn<Customer, String> updateBy_col;

    @FXML
    private TableColumn<Customer, Integer> div_col;

    @FXML
    private Label appt_reminder;

    @FXML
    private Button cust_records_hyperlink;

    @FXML
    private Button Appt_hyperlink;

    @FXML
    private Button reports_hyperlink;

    @FXML
    private Button Add_customer_button;

    @FXML
    private Button modify_customer;

    @FXML
    private Button delete_customer;

    /**
     * Deletes customer from DB.
     */
    @FXML
    private void deleteCustomer() throws SQLException, Exception {

        Customer deleteMe = customer_table_view.getSelectionModel().getSelectedItem();
        System.out.println(deleteMe.getID());

        CustomerDAO.deleteCustomer(deleteMe);
        String name = deleteMe.getName();
        //creates a custom Alert that dynamically populates from customer class object
        Alert custDeleted = new Alert(Alert.AlertType.INFORMATION);
        custDeleted.setTitle("CUSTOMER DELETED");

        custDeleted.setContentText(name + " has been deleted");
        custDeleted.show();
        populateCustomerTable();

    }

    /**
     * modifies customer data.
     */
    @FXML
    private void modifyCustomer(ActionEvent e) {

        try {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/update_customer.fxml"));
            loader.load();

            updateCustomerController UCController = loader.getController();
            UCController.receiveCustomer(customer_table_view.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait();
        } catch (Exception exc) {

            System.out.println(exc.getMessage());
        }


    }

    /**
     * populates customer table.
     */
    @FXML

    private void populateCustomerTable() {
        try {
            id_column.setCellValueFactory(new PropertyValueFactory<>("ID"));
            name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
            address_column.setCellValueFactory(new PropertyValueFactory<>("Address"));
            postal_code_column.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phone_column.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            create_column.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            createBy_col.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            update_col.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            div_col.setCellValueFactory(new PropertyValueFactory<>("division"));
            updateBy_col.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));


            customerList = CustomerDAO.getAllCustomers();

            customer_table_view.setItems(customerList);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public void checkNextmeeting() throws SQLException {

        //get appointments
        apptList = AppointmentDAO.getAllAppointments();
        // iterate through appt list with anon funtion
        apptList.forEach(appt -> {
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
    public void initialize() throws SQLException {

        populateCustomerTable();

        //set default message
        setMessage(appt_reminder, "No Appointments scheduled");
        checkNextmeeting();


    }

    /**
     * This method navigates the add customer form.
     */
    public void goto_add_customer(ActionEvent event) throws IOException {


        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/add_customer_view.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.setFullScreen(true);
        this.stage.show();


    }
}
