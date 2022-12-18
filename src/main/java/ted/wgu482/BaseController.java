package ted.wgu482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    protected Stage stage;
    protected Parent root;

    @FXML
    /**
     * @param event
     * @param sceneName The name (fxml filename) of the scene to load
     * This method loads the scene specified by sceneName.
     */
    protected void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * It displays an error box with the given title, header, and content.
     */
    protected void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * @returns true if the user clicks cancel, false otherwise
     * It displays a confirmation box with the given title, header, and content.
     */
    protected boolean confirmBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        return alert.getResult() == ButtonType.CANCEL;
    }
}
