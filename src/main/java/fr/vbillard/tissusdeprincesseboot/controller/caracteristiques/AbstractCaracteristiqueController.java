package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;


import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public abstract class AbstractCaracteristiqueController implements IModalController {

	@FXML
	protected JFXButton ajouterButton;
	@FXML
	protected JFXButton editerButton;
	@FXML
	protected JFXButton supprimerButton;
	@FXML
	protected JFXButton fermerButton;

	protected static final String PAS_DE_VALEUR = "Pas de valeur";

	protected Stage dialogStage;
	protected boolean okClicked = false;
	protected StageInitializer mainApp;

	protected abstract boolean fieldSaveEmpty();

	protected abstract boolean fieldEditEmpty();

	protected abstract boolean validateSave();

	protected abstract boolean validateEdit();

	protected abstract String fieldAlreadyExists();

	protected abstract String thisField();

	protected abstract void save();

	protected abstract void edit();

	public void handleAddElement() {
		handleSaveOrEdit(true);

	}

	private void handleSaveOrEdit(boolean isSave) {
		if ((isSave && fieldSaveEmpty()) || (!isSave && fieldEditEmpty())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle(PAS_DE_VALEUR);
			alert.setHeaderText(PAS_DE_VALEUR);
			alert.setContentText("Veuillez remplir une valeur");
			alert.showAndWait();
		} else if (isSave && validateSave()) {
			save();
		} else if (!isSave && validateEdit()) {
			edit();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText(fieldAlreadyExists());
			alert.setContentText(thisField() + " existe déjà");

			alert.showAndWait();
		}
	}

	public void handleEditElement() {
		handleSaveOrEdit(false);
	}

	public void handleClose() {
		okClicked = true;
		dialogStage.close();
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@Override
	public FxData result() {
		return null;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;

	}
}
