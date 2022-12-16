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

public class ModifyProductController {

    @FXML
    private TableColumn<Part, Integer> associatedInvCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Double> associatedPriceCol;

    @FXML
    private TextField idTextField;

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
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private TextField priceTextField;

    @FXML
    private ObservableList<Part> associatedParts = null;

    Stage stage;
    Parent root;

    @FXML
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
    void onActionMain(ActionEvent event) throws IOException {
        switchScene(event, "Main.fxml");
    }

    @FXML
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
    void setProduct(Product product) {
        idTextField.setText(Integer.toString(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(Integer.toString(product.getStock()));
        priceTextField.setText(Double.toString(product.getPrice()));
        maxTextField.setText(Integer.toString(product.getMax()));
        minTextField.setText(Integer.toString(product.getMin()));

        associatedParts = product.getAllAssociatedParts();
        setPartTableView();
        setAssociatedPartTableView();
        setPartSearchListener();
    }

    @FXML
    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
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
    private boolean confirmBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        return alert.getResult() == ButtonType.CANCEL;
    }

    @FXML

    private void setPartTableView() {
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void setAssociatedPartTableView() {
        associatedPartTableView.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
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
