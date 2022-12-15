package examples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import examples.Dog;
import examples.DataProvider;

import java.io.IOException;
import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields, continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    @FXML
    void onActionSaveAnimal(ActionEvent event) throws IOException {
    try {
            String behavior = behaviorTextField.getText();
            String breed = breedTextField.getText();
            int id = Integer.parseInt(idTextField.getText());
            int lifespan = Integer.parseInt(lifespanTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            boolean vaccinated = vaccinatedYesRBtn.isSelected();
            String special = null;

            DataProvider.addAnimal(new Dog(id, breed, lifespan, behavior, price, vaccinated, special));
            onActionMainMenu(event);
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter valid data");
            alert.showAndWait();
        }
    }
}
