package ted.wgu482;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * FUTURE ENHANCEMENT - I would extend functionality by adding customers to the inventory management system.
 * Customers would be able to buy products from the inventory, and the inventory would be able to track the number of products sold.
 * */
public class InventoryApplication extends Application {
    /**
     * Main entry point for the application. Sets up the stage and loads the Main scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setUserData("main");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Parent root = null;
                try {
                    if (stage.getScene().getUserData() != null) {
                        System.exit(0);
                    }
                    event.consume();
                    root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene newScene = new Scene(root);
                    newScene.setUserData("main");
                    stage.setScene(newScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }

    /**
     * Launches the application
     *
     * javadoc folder is located in the root directory of the project
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}