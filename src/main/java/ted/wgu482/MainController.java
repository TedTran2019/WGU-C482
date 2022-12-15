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
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import ted.wgu482.model.Inventory;
import ted.wgu482.model.Part;
import ted.wgu482.model.Product;

import java.io.IOException;

public class MainController {
    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, Integer> productInvlevelCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TextField partSearch;

    @FXML
    private TextField productSearch;

    Stage stage;
    Parent root;

    @FXML
    void onActionAddPartForm(ActionEvent event) throws IOException {
        switchScene(event, "AddPart.fxml");
    }

    @FXML
    void onActionAddProductForm(ActionEvent event) throws IOException {
        switchScene(event, "AddProduct.fxml");
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Part not selected", "Please select a part to delete", "Please try again");
            return;
        }
        Part part = partTableView.getSelectionModel().getSelectedItem();

        if (!Inventory.deletePart(part)) {
            errorBox("Part not deleted", "Part couldn't be deleted", "Please try again");
        } else {
            partTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        if (productTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Product not selected", "Please select a product to delete", "Please try again");
            return;
        }
        Product product = productTableView.getSelectionModel().getSelectedItem();

        if (!Inventory.deleteProduct(product)) {
            errorBox("Product not deleted", "Product couldn't be deleted", "Please try again");
        } else {
            productTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionModifyPartForm(ActionEvent event) throws IOException {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();
//        AnimalDetailsMenuController animalDetailsMenuController = loader.getController();
//        animalDetailsMenuController.setAnimal(tableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionModifyProductForm(ActionEvent event) throws IOException {

    }

    @FXML
    private void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void setPartTableCols() {
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void setProductTableCols() {
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvlevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void setPartSearchListener() {
        partSearch.textProperty().addListener((observable, oldValue, newValue) -> {
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
    private void setProductSearchListener() {
        productSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                productTableView.getSelectionModel().clearSelection();
                productTableView.setItems(Inventory.getAllProducts());
                return;
            }

            if (newValue.matches("\\d+")) {
                Product foundProduct = Inventory.lookupProduct(Integer.parseInt(newValue));
                if (foundProduct != null) {
                    productTableView.getSelectionModel().select(foundProduct);
                } else {
                    productTableView.getSelectionModel().clearSelection();
                    errorBox("Product not found", "No product found with ID: " + newValue, "Please try again.");
                }
            } else {
                ObservableList<Product> foundProducts = Inventory.lookupProduct(newValue);
                productTableView.setItems(foundProducts);
                if (foundProducts.size() == 0) {
                    errorBox("Product not found", "No product found containing name: " + newValue, "Please try again.");
                }
            }
        });
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
    private void setListeners() {
        setPartSearchListener();
        setProductSearchListener();
    }

    @FXML
    public void initialize() {
        partTableView.setItems(Inventory.getAllParts());
        setPartTableCols();
        productTableView.setItems(Inventory.getAllProducts());
        setProductTableCols();
        setListeners();
    }
}
