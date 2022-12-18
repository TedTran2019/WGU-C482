package ted.wgu482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ted.wgu482.model.*;

import java.io.IOException;

/**
 * FUTURE ENHANCEMENT - I would extend functionality by adding customers to the inventory management system.
 * Customers would be able to buy products from the inventory, and the inventory would be able to track the number of products sold.
 * */
public class HelloApplication extends Application {
    @Override
    /**
     * Main entry point for the application. Sets up the stage and loads the Main scene.
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * Launches the application
     *
     * javadoc folder is located in the root directory of the project
     */
    public static void main(String[] args) {
        launch();
    }
}