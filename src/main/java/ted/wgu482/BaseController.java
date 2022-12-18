package ted.wgu482;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ted.wgu482.model.Inventory;
import ted.wgu482.model.Part;

import java.io.IOException;

public abstract class BaseController {
    protected Stage stage;
    protected Parent root;

    @FXML
    /**
     * @param event
     * @param sceneName The name (fxml filename) of the scene to load
     * This method loads the scene specified by sceneName.
     */
    protected void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks on the 'Cancel' button.
     * It returns the user to the main screen.
     */
    protected void onActionMain(ActionEvent event) throws IOException {
        switchScene(event, "Main.fxml");
    }

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * It displays an error box with the given title, header, and content.
     */
    protected void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    /**
     * @param title The title of the error box
     * @param header The header of the error box
     * @param content The content of the error box
     * @returns true if the user clicks cancel, false otherwise
     * It displays a confirmation box with the given title, header, and content.
     */
    protected boolean confirmBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        return alert.getResult() == ButtonType.CANCEL;
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
    protected boolean logicalErrors(Double price, int inv, int min, int max) {
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

    /**
     * @param tableView The table to be populated
     * @param parts The list of parts to be added to the table
     * @param id The id column
     * @param name The name column
     * @param inv The stock column
     * @param price The price column
     * This sets a parts table view with the given columns.
     */
    protected void setPartsTableView(TableView<Part> tableView, ObservableList<Part> parts, TableColumn<Part, Integer> id, TableColumn<Part, String> name, TableColumn<Part, Integer> inv, TableColumn<Part, Double> price) {
        tableView.setItems(parts);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    /**
     * This method sets up the listeners for the parts search bar.
     * It selects a specific part with the given ID if the search bar contains only numbers.
     * Otherwise, it filters the parts table by the search bar text.
     * If the search bar is empty, it displays all parts.
     * If the search bar text doesn't match any part, it displays an error message.
     */
    protected void setPartSearchListener(TextField search, TableView<Part> tableView) {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                tableView.getSelectionModel().clearSelection();
                tableView.setItems(Inventory.getAllParts());
                return;
            }

            if (newValue.matches("\\d+")) {
                Part foundPart = Inventory.lookupPart(Integer.parseInt(newValue));
                if (foundPart != null) {
                    tableView.getSelectionModel().select(foundPart);
                } else {
                    tableView.getSelectionModel().clearSelection();
                    errorBox("Part not found", "No Part found with ID: " + newValue, "Please try again.");
                }
            } else {
                ObservableList<Part> foundParts = Inventory.lookupPart(newValue);
                tableView.setItems(foundParts);
                if (foundParts.size() == 0) {
                    errorBox("Part not found", "No part found containing name: " + newValue, "Please try again.");
                }
            }
        });
    }
}
