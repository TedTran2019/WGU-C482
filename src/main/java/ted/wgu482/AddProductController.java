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

public class AddProductController {

    @FXML
    private static int idCounter = 1;

    @FXML
    private TableColumn<Part, Integer> associatedInvCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Double> associatedPriceCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private TextField priceTextField;

    @FXML
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent root;


    @FXML
    /**
     * @param event
     * This method is called when the user clicks the "Add" button.
     * It adds the selected part to the associated parts table.
     * If no part is selected, it displays an error message.
     * If the part is already associated with the product, it displays an error message.
     */
    void onActionAddPart(ActionEvent event) {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            errorBox("No part selected", "Select a part to add", "Please try again");
            return;
        } else if (associatedParts.contains(part)) {
            errorBox("No duplicate parts", "Part already added", "Please try again");
            return;
        }
        associatedParts.add(part);
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
     * @param event
     * This method is called when the user clicks the "Remove Associated Part" button.
     * It removes the selected part from the associated parts table after displaying a confirmation message.
     * If no part is selected, it displays an error message.
     */
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            errorBox("No part selected", "Select a part to remove", "Please try again");
            return;
        }
        if (confirmBox("Remove associated part", "Are you sure you want to remove this part?", "Proceed carefully!")) {
            return;
        }
        associatedParts.remove(part);
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
     * It saves the product and returns the user to the main screen.
     * If the product is invalid, it displays an error.
     */
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

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * It displays an error box with the given title, header, and content.
     */
    private void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    /**
     * @param event
     * @param sceneName The name (fxml filename) of the scene to load
     * This method loads the scene specified by sceneName.
     */
    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    /**
     * Prepares the part table
     * */
    private void setPartTableView() {
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    /**
     * Prepares the associated part table
     * */
    private void setAssociatedPartTableView() {
        associatedPartTableView.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * @returns true if the user clicks cancel, false otherwise
     * It displays a confirmation box with the given title, header, and content.
     */
    private boolean confirmBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        return alert.getResult() == ButtonType.CANCEL;
    }

    @FXML
    /**
     * Sets up tables and listeners
     * */
    public void initialize() {
        setPartTableView();
        setAssociatedPartTableView();
        setPartSearchListener();
    }
}
