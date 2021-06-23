package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Main class of application.
 */
public class Main extends Application {

    /**
     * Start method is entry point for root view.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Login_view.fxml"));
        primaryStage.setTitle("CREATED BY CHRIS BRUNDIGE");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root, 800, 1200));
        primaryStage.show();
    }

    /**
     * Main method for app entry
     */
    public static void main(String[] args) {


        //test for language detection
        //Locale.setDefault(new Locale("fr", "FR"));

        launch(args);


    }
}
