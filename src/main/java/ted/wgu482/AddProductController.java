package ted.wgu482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ted.wgu482.model.*;

import java.io.IOException;

public class AddProductController extends ProductsController {

    @FXML
    private static int idCounter = 1;

    @FXML
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method is called when the user clicks on the 'Save' button.
     * It saves the product and returns the user to the main screen.
     * If the product is invalid, it displays an error.
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            double price = Double.parseDouble(priceTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            String name = nameTextField.getText();
            if (logicalErrors(price, inv, min, max)) { return; }
            Product newProduct = new Product(idCounter, name, price, inv, min, max);
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(newProduct);
            idCounter++;
            onActionMain(event);
        } catch (NumberFormatException e) {
            errorBox("Numeric Error", e.getMessage(), "Please enter valid numbers");
        } catch (Exception e) {
            errorBox("Error", e.getMessage(), "Please enter valid data");
        }
    }

    /**
     * Sets up tables and listeners
     * */
    @FXML
    public void initialize() {
        setPartsTableView(partTableView, Inventory.getAllParts(), partIDCol, partNameCol, invCol, priceCol);
        setPartsTableView(associatedPartTableView, associatedParts, associatedPartIDCol, associatedPartNameCol, associatedInvCol, associatedPriceCol);
        setPartSearchListener(partSearchBar, partTableView);
    }
}
