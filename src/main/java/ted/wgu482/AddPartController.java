package ted.wgu482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ted.wgu482.model.InHouse;
import ted.wgu482.model.Inventory;
import ted.wgu482.model.Outsourced;
import ted.wgu482.model.Part;

import java.io.IOException;

public class AddPartController {
    @FXML
    private static int idCounter = 1;

    @FXML
    private Label MIDorCNameLabel;

    @FXML
    private TextField MIDorCNameTextField;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private ToggleGroup partType;

    @FXML
    private TextField priceTextField;

    Stage stage;
    Parent root;

    @FXML
    void onActionInHouse(ActionEvent event) {
        MIDorCNameLabel.setText("Machine ID");
    }

    @FXML
    void onActionOutsourced(ActionEvent event) {
        MIDorCNameLabel.setText("Company Name");
    }

    @FXML
    void onActionMain(ActionEvent event) throws IOException {
        switchScene(event, "Main.fxml");
    }

    @FXML
    private void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private boolean logicalErrors(Double price, int inv, int min, int max) {
        if (min >= max) {
            errorBox("Error", "Max has to be greater than min", "Please correct the values and try again");
            return true;
        }
        else if (inv < min || inv > max) {
            errorBox("Error", "Inventory Error", "Inventory must be between min and max");
            return true;
        } else if (price < 0) {
            errorBox("Error", "Price Error", "Price can't be negative");
            return true;
        }
        return false;
    }

    @FXML
    void onActionSave(ActionEvent event) {
        try {
            double price = Double.parseDouble(priceTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            String name = nameTextField.getText();
            if (logicalErrors(price, inv, min, max)) { return; }
            Part newPart;
            if (inHouseRBtn.isSelected()) {
                int machineID = Integer.parseInt(MIDorCNameTextField.getText());
                newPart = new InHouse(idCounter, name, price, inv, min, max, machineID);

            } else {
                String companyName = MIDorCNameTextField.getText();
                newPart = new Outsourced(idCounter, name, price, inv, min, max, companyName);
            }
            Inventory.addPart(newPart);
            idCounter++;
            onActionMain(event);
        } catch (NumberFormatException e) {
            errorBox("Numeric Error", e.getMessage(), "Please enter valid numbers");
        } catch (Exception e) {
            errorBox("Error", e.getMessage(), "Please enter valid data");
        }
    }

    @FXML
    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
