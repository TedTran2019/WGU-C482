package ted.wgu482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    protected ObservableList<Part> associatedParts;
}
