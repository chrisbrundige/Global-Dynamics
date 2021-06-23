package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDAO;
import Model.Country;
import Model.FirstLevel;
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
import utils.sharedFunctions;

/**
 * Class is Controller for adding customers.
 */

public class add_customer_controller {
    Stage stage;
    Parent scene;


    @FXML
    private TextField add_customer_id;


    @FXML
    private TextField add_customer_phone;

    @FXML
    private TextField add_customer_address;

    @FXML
    private TextField add_customer_postal;

    @FXML
    private TextField add_customer_name;

    @FXML
    private ComboBox<Country> country_combo;

    @FXML
    private ComboBox<FirstLevel> first_level_combo;

    @FXML
    private ObservableList<FirstLevel> FirstLevelDataObservableList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Country> CountryList = FXCollections.observableArrayList();

    @FXML
    private Button add_customer_button;

    /**
     * Method populates first-level div combo boxes.
     */
    @FXML
    private void populateFirstDiv() {

        FirstLevelDataObservableList.clear();
        first_level_combo.setItems(FirstLevelDataObservableList);


        try {
            if (country_combo != null) {
                FirstLevelDataObservableList.addAll(FirstLevelDAO.getDivByCountry(country_combo.getValue().getId()));
            } else {
                FirstLevelDataObservableList.addAll(FirstLevelDAO.getAllFirstLevelData());
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        first_level_combo.setItems(FirstLevelDataObservableList);


    }

    /**
     * Method populates country combo boxes.
     */
    private void populateCountry() {

        //populate country combo box
        try {

            CountryList.addAll(CountryDAO.getAllCountries());
            country_combo.setItems(CountryList);
            populateFirstDiv();


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }

    /**
     * Method utilizes DAO to add user supplied information to create a new customer.
     */
    @FXML
    void add_customer(ActionEvent event) throws Exception {

        try {


            int ID = sharedFunctions.generateRand();
            String name = add_customer_name.getText();
            String address = add_customer_address.getText();
            String postalCode = add_customer_postal.getText();
            String phone = add_customer_phone.getText();
            String createdBy = "admin";
            String lastUpdatedBy = "admin";
            int division = first_level_combo.getValue().getId();

            if (add_customer_name.getText().isEmpty() || add_customer_address.getText().isEmpty() || add_customer_postal.getText().isEmpty() || add_customer_phone.getText().isEmpty() || country_combo.getValue() == null || first_level_combo.getValue() == null) {
                System.out.println("this is working");
                Alert missingFields = new Alert(Alert.AlertType.INFORMATION);
                missingFields.setTitle("please fill out all fields");
                missingFields.setContentText("Please fill out all fields");
                missingFields.showAndWait();

            } else {

                CustomerDAO.addCustomer(ID, name, address, postalCode, phone, createdBy, lastUpdatedBy, division);
                System.out.println(event.getEventType());
                this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/customer_record_view.fxml"));
                this.stage.setScene(new Scene(this.scene));
                this.stage.show();

            }
        } catch (Exception e) {

            Alert missingFields = new Alert(Alert.AlertType.ERROR);
            missingFields.setTitle("please fill out all fields");
            missingFields.setContentText("Please fill out all fields");
            System.out.println(e.getMessage());
            missingFields.showAndWait();

        }


    }

    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() {

        populateCountry();


    }


}
