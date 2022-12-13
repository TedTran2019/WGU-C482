package ted.wgu482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAnimalMenuController {
    @FXML
    private TextField behaviorTextField;

    @FXML
    private TextField breedTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField lifespanTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private RadioButton vaccinatedNoRBtn;

    @FXML
    private RadioButton vaccinatedYesRBtn;

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
    @FXML
    void onActionSaveAnimal(ActionEvent event) throws IOException {
    }
}
