package fr.vbillard.tissusdeprincesseboot.controller.common;

import static fr.vbillard.tissusdeprincesseboot.utils.FxUtils.floatFromJFXTextField;
import static fr.vbillard.tissusdeprincesseboot.utils.FxUtils.intFromJFXTextField;

import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Component
public class SetQuantiteDialogController implements IModalController {
	private Stage dialogStage;
	private float required;
	private float available;
	private FxData result;
	private float longueur;
	private float value;
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
		longueurValue.setTextFormatter(CustomSpinner.getLongFormatter());
	}

	@FXML
	public void handleValidate() {
		value = floatFromJFXTextField(longueurValue);
		if (value > available) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(initializer.getPrimaryStage());
			alert.setTitle("Consommé > en stock");
			alert.setHeaderText("Valeur supérieur au stock");
			alert.setContentText("On ne coud pas des tissus qu'on ne possède pas");
			alert.showAndWait();
		} else if (value <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(initializer.getPrimaryStage());
			alert.setTitle("Vous devez spécifier une valeur");
			alert.setHeaderText("Vous devez spécifier une valeur");
			alert.setContentText("On ne peux pas coudre une longueur nulle");
			alert.showAndWait();
		} else {
			result = new FxData();
			result.setQuantiteRequise(value);
			dialogStage.close();
		}

	}

	public SetQuantiteDialogController(StageInitializer initializer) {
		this.initializer = initializer;
	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	@FXML
	public void handleAuto() {
		longueurValue.setText(Float.toString(Math.min(required, available)));
	}

	@Override
	public FxData result() {
		//result.setLongueurRequise(longueur);
		return result;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		this.longueur = 0;
			this.required = data.getQuantiteRequise();

			this.available = data.getFourniture().getQuantite();
			requisLabel.setText(required + data.getFourniture().getUnite());
			dispoLabel.setText(available + data.getFourniture().getUnite());



		longueurValue.setText("0");

	}

}