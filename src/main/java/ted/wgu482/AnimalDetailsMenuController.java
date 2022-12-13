package ted.wgu482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalDetailsMenuController {
    @FXML
    private Label behaviorLabel;

    @FXML
    private Label breedLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label lifespanLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label specialLabel;

    @FXML
    private Label vaccinatedLabel;

    Stage stage;
    Parent root;

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        System.out.println(getClass());
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
