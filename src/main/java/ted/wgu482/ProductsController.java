package ted.wgu482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ted.wgu482.model.Part;

public abstract class ProductsController extends BaseController {
    @FXML
    protected TableColumn<Part, Integer> associatedInvCol;

    @FXML
    protected TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    protected TableColumn<Part, String> associatedPartNameCol;

    @FXML
    protected TableView<Part> associatedPartTableView;

    @FXML
    protected TableColumn<Part, Double> associatedPriceCol;

    @FXML
    protected TableColumn<Part, Integer> invCol;

    @FXML
    protected TextField invTextField;

    @FXML
    protected TextField maxTextField;

    @FXML
    protected TextField minTextField;

    @FXML
    protected TextField nameTextField;

    @FXML
    protected TableColumn<Part, Integer> partIDCol;

    @FXML
    protected TableColumn<Part, String> partNameCol;

    @FXML
    protected TextField partSearchBar;

    @FXML
    protected TableView<Part> partTableView;

    @FXML
    protected TableColumn<Part, Double> priceCol;

    @FXML
    protected TextField priceTextField;

    @FXML
    protected ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method is called when the user clicks the "Add" button.
     * It adds the selected part to the associated parts table.
     * If no part is selected, it displays an error message.
     * If the part is already associated with the product, it displays an error message.
     * @param event
     */
    @FXML
    protected void onActionAddPart(ActionEvent event) {
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

    /**
     * This method is called when the user clicks the "Remove Associated Part" button.
     * It removes the selected part from the associated parts table after displaying a confirmation message.
     * If no part is selected, it displays an error message.
     * @param event
     */
    @FXML
    protected void onActionRemoveAssociatedPart(ActionEvent event) {
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
}
