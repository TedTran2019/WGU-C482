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

public class ModifyProductController extends ProductsController {
    @FXML
    private TextField idTextField;

    @FXML
    /**
     * @param event
     * This method is called when the user clicks on the 'Save' button.
     * It saves the product and returns the user to the main screen.
     * If the product is invalid, it displays an error.
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
            Product newProduct = new Product(id, name, price, inv, min, max);
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            updateProductInList(newProduct, id);
            onActionMain(event);
        } catch (NumberFormatException e) {
            errorBox("Numeric Error", e.getMessage(), "Please enter valid numbers");
        } catch (Exception e) {
            errorBox("Error", e.getMessage(), "Please enter valid data");
        }
    }

    @FXML
    /**
     * @param newProduct the product to replace the old product
     * @param id the id of the product to replace
     * Replaces the old product found by ID with the new product
     */
    private void updateProductInList(Product newProduct, int id) {
        int idx = 0;
        for (Product product: Inventory.getAllProducts()) {
            if (product.getId() == id) {
                Inventory.updateProduct(idx, newProduct);
                break;
            }
            idx++;
        }
    }

    @FXML
    /**
     * @param product product to be modified
     * Sets text fields to the product's values
     * Prepares tables and sets up listener for search bar
     */
    void setProduct(Product product) {
        idTextField.setText(Integer.toString(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(Integer.toString(product.getStock()));
        priceTextField.setText(Double.toString(product.getPrice()));
        maxTextField.setText(Integer.toString(product.getMax()));
        minTextField.setText(Integer.toString(product.getMin()));

        associatedParts = product.getAllAssociatedParts();
        setPartsTableView(partTableView, Inventory.getAllParts(), partIDCol, partNameCol, invCol, priceCol);
        setPartsTableView(associatedPartTableView, associatedParts, associatedPartIDCol, associatedPartNameCol, associatedInvCol, associatedPriceCol);
        setPartSearchListener();
    }

    @FXML
    /**
     * This method sets up the listeners for the parts search bar.
     * It selects a specific part with the given ID if the search bar contains only numbers.
     * Otherwise, it filters the parts table by the search bar text.
     * If the search bar is empty, it displays all parts.
     * If the search bar text doesn't match any part, it displays an error message.
     */
    private void setPartSearchListener() {
        partSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                partTableView.getSelectionModel().clearSelection();
                partTableView.setItems(Inventory.getAllParts());
                return;
            }

            if (newValue.matches("\\d+")) {
                Part foundPart = Inventory.lookupPart(Integer.parseInt(newValue));
                if (foundPart != null) {
                    partTableView.getSelectionModel().select(foundPart);
                } else {
                    partTableView.getSelectionModel().clearSelection();
                    errorBox("Part not found", "No Part found with ID: " + newValue, "Please try again.");
                }
            } else {
                ObservableList<Part> foundParts = Inventory.lookupPart(newValue);
                partTableView.setItems(foundParts);
                if (foundParts.size() == 0) {
                    errorBox("Part not found", "No part found containing name: " + newValue, "Please try again.");
                }
            }
        });
    }
}
