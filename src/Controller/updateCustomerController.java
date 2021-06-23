package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDAO;
import Model.Country;
import Model.Customer;
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

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class is Controller for updating Customer.
 */

public class updateCustomerController {

    @FXML
    private TextField id_col;

    @FXML
    private TextField phone_col;

    @FXML
    private TextField address_col;

    @FXML
    private TextField postal_col;

    @FXML
    private TextField name_col;

    @FXML
    private ComboBox<FirstLevel> firstLevel_combo;

    @FXML
    ComboBox<Country> country_combo;

    @FXML
    private Button add_customer_button;

    @FXML
    private ObservableList<FirstLevel> FirstLevelDataObservableList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Country> CountryList = FXCollections.observableArrayList();

    private Customer currentCustomer;
    Stage stage;
    Parent scene;

    /**
     * method populates first div.
     */
    @FXML

    private void populateFirstDiv() {

        FirstLevelDataObservableList.clear();
        firstLevel_combo.setItems(FirstLevelDataObservableList);

        try {
            if (country_combo != null) {
                FirstLevelDataObservableList.addAll(FirstLevelDAO.getDivByCountry(country_combo.getValue().getId()));
            } else {
                FirstLevelDataObservableList.addAll(FirstLevelDAO.getAllFirstLevelData());
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        firstLevel_combo.setItems(FirstLevelDataObservableList);


    }

    /**
     * method populate first country.
     */
    private void populateCountry() {

        //populate country combo box
        try {


            CountryList.addAll(CountryDAO.getAllCountries());
            country_combo.setItems(CountryList);


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }

    /**
     * method receives customer data.
     */
    public void receiveCustomer(Customer customer) throws SQLException {

        //get ID, Name , Address, phone #, postal code,
        id_col.setText(String.valueOf(customer.getID()));
        name_col.setText(customer.getName());
        address_col.setText(customer.getAddress());
        phone_col.setText((customer.getPhone()));
        postal_col.setText(customer.getPostalCode());
        //update "global" customer object with data passed in.
        currentCustomer = customer;
        //populate first level with preselected data
        FirstLevel flDiv = FirstLevelDAO.getFirstLevelByCustomer(customer);
        firstLevel_combo.setValue(flDiv);
        Country selCountry = CountryDAO.getCountryFromDiv(customer.getDivision());
        country_combo.setValue(selCountry);
        //int preselectedCountry = Integer.parseInt(FirstLevelDAO.getFirstLevelByCustomer(customer));
        //subtract 1 from returned index to change to a O based index
        //country_combo.getSelectionModel().select(preselectedCountry - 1);
    }

    /**
     * method updates customer information.
     */
    @FXML
    void update_customer(ActionEvent e) throws IOException, SQLException {
        currentCustomer.setID(Integer.parseInt(id_col.getText()));
        currentCustomer.setAddress(address_col.getText());
        currentCustomer.setName(name_col.getText());
        currentCustomer.setPhone(phone_col.getText());
        currentCustomer.setPostalCode(postal_col.getText());
        currentCustomer.setDivision(firstLevel_combo.getValue().getId());

        if (name_col.getText().isEmpty() || address_col.getText().isEmpty() || postal_col.getText().isEmpty() || phone_col.getText().isEmpty()) {
            Alert missingFields = new Alert(Alert.AlertType.ERROR);
            missingFields.setContentText("Please fill out all fields");
            missingFields.showAndWait();

        } else {


            CustomerDAO.updateCustomer(currentCustomer);
            this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/customer_record_view.fxml"));
            this.stage.setScene(new Scene(this.scene));
            this.stage.setFullScreen(true);
            this.stage.show();
        }


    }

    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() throws SQLException {

        firstLevel_combo.setItems(FirstLevelDAO.getAllFirstLevelData());
        country_combo.setItems(CountryDAO.getAllCountries());


    }


}



