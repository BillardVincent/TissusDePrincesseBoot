package fr.vbillard.tissusdeprincesseboot.controller.common;

import com.jfoenix.controls.JFXTextField;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.*;


import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Component
public class SetLongueurDialogController implements IModalController {
	private Stage dialogStage;
	private int required;
	private int available;
	private FxData result;
	private int longueur;
	private int value;
	private final StageInitializer initializer;

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
		longueurValue.setTextFormatter(CustomSpinner.getFormatter());
	}

	@FXML
	public void handleValidate() {
		value = intFromJFXTextField(longueurValue);
		if (value > available) {
			ShowAlert.warn(initializer.getPrimaryStage(),"Consommé > en stock", "Valeur supérieur au stock",
					"On ne coud pas des tissus qu'on ne possède pas");
		} else if (value <= 0) {
			ShowAlert.warn(initializer.getPrimaryStage(),"Vous devez spécifier une valeur",
					"Vous devez spécifier une valeur", "On ne peux pas coudre une longueur nulle");
		} else {
			result = new FxData();
			result.setLongueurRequise(value);
			dialogStage.close();
		}

	}

	public SetLongueurDialogController(StageInitializer initializer) {
		this.initializer = initializer;
	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	@FXML
	public void handleAuto() {
		longueurValue.setText(Integer.toString(Math.min(required, available)));
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
		this.required = data.getLongueurRequise();
		this.available = data.getTissu().getLongueur();
		requisLabel.setText(required + "cm");
		dispoLabel.setText(available + "cm");

		longueurValue.setText("0");

	}

}
