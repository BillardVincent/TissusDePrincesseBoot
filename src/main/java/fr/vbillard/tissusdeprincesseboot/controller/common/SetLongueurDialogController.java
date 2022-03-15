package fr.vbillard.tissusdeprincesseboot.controller.common;

import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.IntegerSpinner;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
//import javafx.scene.control.Spinner;
//import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

@Component
public class SetLongueurDialogController implements IModalController {
	private Stage dialogStage;
	private int required;
	private int available;
	private FxData result;
	private int longueur;
	private int value;
	private StageInitializer initializer;

	@FXML
	private Label requisLabel;
	@FXML
	private Label dispoLabel;
	@FXML
	private JFXTextField longueurValue;
	@FXML
	private JFXButton valdateBtn;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private JFXButton auto;

	@FXML
	private void initialize() {
		longueurValue.setTextFormatter(IntegerSpinner.getFormatter());
	}

	@FXML
	public void handleValidate() {
		if (value > available) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(initializer.getPrimaryStage());
			alert.setTitle("Consommé > en stock");
			alert.setHeaderText("Valeur supérieur au stock");
			alert.setContentText("On ne coud pas des tissus qu'on ne possède pas");
			alert.showAndWait();
		} else {
			result = new FxData();
			result.setLongueurRequise(value);
			dialogStage.close();
		}

	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	@FXML
	public void handleAuto() {
		longueurValue.setText(Integer.toString(required > available ? available : required));
	}

	@Override
	public FxData result() {
		result.setLongueurRequise(longueur);
		return result;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		this.longueur = 0;
		this.required = data.getLongueurRequise();
		this.available = data.getTissu().getLongueur();
		requisLabel.setText(required + "cm");
		dispoLabel.setText(available + "cm");

		longueurValue.setText("0");

	}

}