package ted.wgu482;

import javafx.event.ActionEvent;
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

    /**
     * This method is called when the user clicks on the 'In-House' radio button.
     * It changes the label to 'Machine ID'.
     * @param event
     */
    @FXML
    protected void onActionInHouse(ActionEvent event) {
        MIDorCNameLabel.setText("Machine ID");
    }

    /**
     * This method is called when the user clicks on the 'Outsourced' radio button.
     * It changes the label to 'Company Name'.
     * @param event
     */
    @FXML
    protected void onActionOutsourced(ActionEvent event) {
        MIDorCNameLabel.setText("Company Name");
    }
}
