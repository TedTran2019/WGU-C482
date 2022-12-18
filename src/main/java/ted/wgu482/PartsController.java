package ted.wgu482;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public abstract class PartsController extends BaseController {
    @FXML
    protected Label MIDorCNameLabel;

    @FXML
    protected TextField MIDorCNameTextField;

    @FXML
    protected RadioButton inHouseRBtn;

    @FXML
    protected TextField invTextField;

    @FXML
    protected TextField maxTextField;

    @FXML
    protected TextField minTextField;

    @FXML
    protected TextField nameTextField;

    @FXML
    protected RadioButton outsourcedRBtn;

    @FXML
    protected ToggleGroup partType;

    @FXML
    protected TextField priceTextField;
}
