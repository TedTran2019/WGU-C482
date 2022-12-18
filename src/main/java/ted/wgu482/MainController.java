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

public class MainController extends BaseController {
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

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Add button in the Parts section.
     * It loads the AddPart scene.
     */
    void onActionAddPartForm(ActionEvent event) throws IOException {
        switchScene(event, "AddPart.fxml");
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Modify button in the Parts section.
     * It loads the ModifyPart scene.
     */
    void onActionAddProductForm(ActionEvent event) throws IOException {
        switchScene(event, "AddProduct.fxml");
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Delete button in the Parts section.
     * It deletes the selected part from the inventory if a part is selected.
     * It displays an error message if no part is selected.
     */
    void onActionDeletePart(ActionEvent event) {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Part not selected", "Please select a part to delete", "Please try again");
            return;
        }
        Part part = partTableView.getSelectionModel().getSelectedItem();
        if (confirmBox("Delete Part", "Are you sure you want to delete " + part.getName() + "?", "This action cannot be undone.")) {
            return;
        }

        if (!Inventory.deletePart(part)) {
            errorBox("Part not deleted", "Part couldn't be deleted", "Please try again");
        } else {
            partTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Delete button in the Products section.
     * It deletes the selected product from the inventory if a product is selected.
     * It displays an error message if no product is selected or if the product has associated parts.
     */
    void onActionDeleteProduct(ActionEvent event) {
        if (productTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Product not selected", "Please select a product to delete", "Please try again");
            return;
        }
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (confirmBox("Delete Product", "Are you sure you want to delete product " + product.getName() + "?", "This action cannot be undone.")) {
            return;
        }

        if (!product.getAllAssociatedParts().isEmpty()) {
            errorBox("Product not deleted", "Product has associated parts", "Please remove associated parts and try again");
        }
        else if (!Inventory.deleteProduct(product)) {
            errorBox("Product not deleted", "Product couldn't be deleted", "Please try again");
        } else {
            productTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Exit button.
     * It exits the application.
     */
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Modify button in the Parts section.
     * It loads the ModifyPart scene.
     */
    void onActionModifyPartForm(ActionEvent event) throws IOException {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Part not selected", "Please select a part to modify", "Please try again");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();
        ModifyPartController modifyPartController = loader.getController();
        modifyPartController.setPart(partTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    /**
     * @param event
     * This method is called when the user clicks the Modify button in the Products section.
     * It loads the ModifyProduct scene.
     */
    void onActionModifyProductForm(ActionEvent event) throws IOException {
        if (productTableView.getSelectionModel().getSelectedItem() == null) {
            errorBox("Product not selected", "Please select a product to modify", "Please try again");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();
        ModifyProductController modifyProductController = loader.getController();
        modifyProductController.setProduct(productTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    /**
     * This method sets the columns of the Parts table.
     */
    private void setPartTableCols() {
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    /**
     * This method sets the columns of the Products table.
     */
    private void setProductTableCols() {
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvlevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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
    /**
     * This method sets up the listeners for the products search bar.
     * It selects a specific product with the given ID if the search bar contains only numbers.
     * Otherwise, it filters the products table by the search bar text.
     * If the search bar is empty, it displays all products.
     * If the search bar text doesn't match any product, it displays an error message.
     */
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
    /**
     * Sets up listeners for the parts search bar and products search bar.
     * */
    private void setListeners() {
        setPartSearchListener();
        setProductSearchListener();
    }

    @FXML
    /**
     * Prepares tables and sets up the listeners.
     */
    public void initialize() {
        partTableView.setItems(Inventory.getAllParts());
        setPartTableCols();
        productTableView.setItems(Inventory.getAllProducts());
        setProductTableCols();
        setListeners();
    }
}
