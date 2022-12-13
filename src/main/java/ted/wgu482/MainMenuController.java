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

public class MainMenuController {

    Stage stage;
    Parent root;
    @FXML
    void onActionCreateAnimal(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        System.out.println(getClass());
        root = FXMLLoader.load(getClass().getResource("CreateAnimalMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionDisplayAnimal(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {

    }
}