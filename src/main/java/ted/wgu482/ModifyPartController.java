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

public class ModifyPartController {

    @FXML
    private Label MIDorCNameLabel;

    @FXML
    private TextField MIDorCNameTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField maxTextField;

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
    private void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int id = Integer.parseInt(idTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            String name = nameTextField.getText();
            if (logicalErrors(price, inv, min, max)) { return; }
            Part newPart;
            if (inHouseRBtn.isSelected()) {
                int machineID = Integer.parseInt(MIDorCNameTextField.getText());
                newPart = new InHouse(id, name, price, inv, min, max, machineID);

            } else {
                String companyName = MIDorCNameTextField.getText();
                newPart = new Outsourced(id, name, price, inv, min, max, companyName);
            }
            updatePartInList(newPart, id);
            onActionMain(event);
        } catch (NumberFormatException e) {
            errorBox("Numeric Error", e.getMessage(), "Please enter valid numbers");
        } catch (Exception e) {
            errorBox("Error", e.getMessage(), "Please enter valid data");
        }
    }

    @FXML
    private void updatePartInList(Part newPart, int id) {
        int idx = 0;
        for (Part part: Inventory.getAllParts()) {
            if (part.getId() == id) {
                Inventory.updatePart(idx, newPart);
                break;
            }
            idx++;
        }
    }

    @FXML
    void setPart(Part part) {
        idTextField.setText(Integer.toString(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(Integer.toString(part.getStock()));
        priceTextField.setText(Double.toString(part.getPrice()));
        maxTextField.setText(Integer.toString(part.getMax()));
        minTextField.setText(Integer.toString(part.getMin()));
        if (part instanceof InHouse) {
            inHouseRBtn.setSelected(true);
            MIDorCNameLabel.setText("Machine ID");
            MIDorCNameTextField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            outsourcedRBtn.setSelected(true);
            MIDorCNameLabel.setText("Company Name");
            MIDorCNameTextField.setText(((Outsourced) part).getCompanyName());
        }
    }

    @FXML
    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize() {
        System.out.println("ModifyPartController initialized");
    }
}
