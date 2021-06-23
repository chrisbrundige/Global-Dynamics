package Controller;

import DAO.AppointmentDAO;
import DAO.UserDao;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.getDefault;

/**
 * Class is Controller that provides a login screen and user authentication.
 */

public class LoginController {
    Stage stage;
    Parent scene;
    Integer count = 0;

    @FXML
    private Label zone_id_label;

    @FXML
    private TextField username_login;

    @FXML
    private PasswordField password_login;

    @FXML
    private Button login_button;

    /**
     * method navigates to customer view on click .
     */
    @FXML
    private void goToCustomerView(ActionEvent event) throws IOException {


        if (verifyUser()) {
            try {
                checkNextmeeting();
            } catch (Exception e) {

                System.out.println(e.getMessage());

            }


            this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/Views/customer_record_view.fxml"));
            this.stage.setScene(new Scene(this.scene));
            this.stage.show();


        } else {

            System.out.println("Access Error");
        }

    }

    /**
     * method verifies that user credentials are accurate.
     *
     * @return boolean
     */
    @FXML
    public boolean verifyUser() {
        String usernameInput = this.username_login.getText();
        String passwordInput = this.password_login.getText();
        ResourceBundle rb = ResourceBundle.getBundle("babels", getDefault());

        try {
            User user = UserDao.getUser(usernameInput);

            if (user != null && user.getUserName().equals(usernameInput) && user.getPassword().equals(passwordInput)) {
                //records login as success
                recordLogin(user, true);

                return true;
            } else {

                if (Locale.getDefault().toString().equals("fr_FR")) {
                    Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                    a1.setTitle(rb.getString("accessDenied"));
                    a1.setContentText(rb.getString("accessDenied"));
                    a1.show();
                    System.out.println("login failed");
                } else {

                    Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                    a1.setTitle("access denied");
                    a1.setContentText("access denied");
                    a1.show();
                    System.out.println("login failed");

                }
                recordLogin(user, false);


                return false;
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return false;


    }

    /**
     * method records login .
     *
     * @param bool
     * @param user
     */
    @FXML
    public static void recordLogin(User user, boolean bool) {
        String access = bool ? "SUCCESS" : "FAIL";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            FileWriter ghostWriter = new FileWriter("src/login_activity.txt", true);
            ghostWriter.write("\n" + user.getUserName() + "|" + timestamp.toString() + "|" + access);
            ghostWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * overloaded method records login .
     *
     * @param bool
     */
    @FXML
    public static void recordLogin(boolean bool) {
        String access = bool ? "SUCCESS" : "FAIL";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            FileWriter ghostWriter = new FileWriter("src/login_activity.txt");
            ghostWriter.write("Unknown User " + "|" + timestamp.toString() + "|" + access);
            ghostWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * method changes language based on user location .
     */
    @FXML
    public void changeLang() {

        ResourceBundle rb = ResourceBundle.getBundle("babels", Locale.getDefault());

        this.zone_id_label.setText(getDefault().getCountry());


        System.out.println(Locale.getDefault());
        if (Locale.getDefault().toString().equals("fr_FR")) {


            username_login.setPromptText(rb.getString("username"));
            password_login.setPromptText(rb.getString("password"));
            login_button.setText(rb.getString("login"));

        }

    }

    /**
     * method runs to show next meeting.
     */
    public void checkNextmeeting() throws SQLException {

        //get appointments
        ObservableList<Appointment> apptList = AppointmentDAO.getAllAppointments();
        // iterate through appt list with anon funtion
        Alert nxtMtg = new Alert(Alert.AlertType.INFORMATION);
        apptList.forEach(appt -> {
            if (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                nxtMtg.setTitle("Future Meeting Status");
                nxtMtg.setContentText("USER " + appt.getUserID() + " has a meeting at : " + appt.getStart());
                nxtMtg.showAndWait();
                count++;

            } else {

                if (this.count == 0) {

                    nxtMtg.setTitle("Future Meeting Status");
                    nxtMtg.setContentText("NO APPOINTMENTS SCHEDULED FOR THE NEXT 15 MIN");
                    nxtMtg.showAndWait();
                    count++;

                }
            }
        });

    }


    /**
     * Method runs first when Class is called.
     * it calls populate country to load on page load.
     */
    public void initialize() {
        changeLang();


    }


}