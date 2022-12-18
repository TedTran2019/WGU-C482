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

public class AddPartController extends PartsController {
    @FXML
    private static int idCounter = 1;

    @FXML
    /**
     * @param event
     * This method is called when the user clicks on the 'In-House' radio button.
     * It changes the label to 'Machine ID'.
     */
    void onActionInHouse(ActionEvent event) {
        MIDorCNameLabel.setText("Machine ID");
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks on the 'Outsourced' radio button.
     * It changes the label to 'Company Name'.
     */
    void onActionOutsourced(ActionEvent event) {
        MIDorCNameLabel.setText("Company Name");
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks on the 'Cancel' button.
     * It returns the user to the main screen.
     */
    void onActionMain(ActionEvent event) throws IOException {
        switchScene(event, "Main.fxml");
    }

    @FXML
    /**
     * @param price The price to be validated
     * @param min The minimum value to be validated
     * @param max The maximum value to be validated
     * @param inv The inventory amount to be validated
     * @returns true if there is a logical error, false otherwise
     * This method checks if max is greater than min, if price is positive, and if inv is between min and max.
     */
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
    /**
     * @param event
     * This method is called when the user clicks on the 'Save' button.
     * It saves the part and returns the user to the main screen.
     * If the part is invalid, it displays an error.
     */
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
}
