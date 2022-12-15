package examples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import examples.Animal;
import examples.Dog;

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

    @FXML
    void setAnimal(Animal animal) {
        breedLabel.setText(animal.getBreed());
        idLabel.setText(Integer.toString(animal.getId()));
        lifespanLabel.setText(Integer.toString(animal.getLifespan()));
        priceLabel.setText(Double.toString(animal.getPrice()));
        vaccinatedLabel.setText(Boolean.toString(animal.isVaccinated()));
        behaviorLabel.setText(animal.getBehavior());
        if (animal instanceof Dog) {
            specialLabel.setText(((Dog) animal).getSpecialAbility());
        } else {
            specialLabel.setText("N/A");
        }
    }

    @FXML
    public void intiialize() {
        System.out.println("AnimalDetailsMenuController initialized");
    }
}
