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

public class ModifyPartController extends PartsController {

    @FXML
    protected TextField idTextField;

    @FXML
    /**
     * RUNTIME ERROR - I originally tried to just modify the existing part, but that didn't work since there are
     * two different types of parts. I fixed this issue by just creating an entirely new part and using it to replace
     * the old part.
     *
     * @param event
     * This method is called when the user clicks on the 'Save' button.
     * It saves the part and returns the user to the main screen.
     * If the part is invalid, it displays an error.
     */
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
    /**
     * @param newPart the part to replace the old part
     * @param id the id of the part to replace
     * Replaces the old part found by ID with the new part
     */
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
    /**
     * @param part the part to be modified
     * Sets all the text fields to the part's values
     */
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
}
