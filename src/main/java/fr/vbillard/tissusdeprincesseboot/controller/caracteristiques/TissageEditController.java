package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class TissageEditController implements IModalController {

	@FXML
	private JFXListView<String> listTissages;

	@FXML
	private JFXTextField newTissage;
	@FXML
	private JFXTextField editTissage;

	@FXML
	private JFXButton ajouterButton;
	@FXML
	private JFXButton editerButton;
	@FXML
	private JFXButton supprimerButton;
	@FXML
	private JFXButton fermerButton;

	private Stage dialogStage;
	private boolean okClicked = false;
	@Autowired
	TissageService tissageService;
	StageInitializer mainApp;

	String editedTissage;

	ObservableList<String> allTissages;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		allTissages = tissageService.getAllObs();
		listTissages.setItems(allTissages);
		listTissages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(StageInitializer mainApp) {
		this.mainApp = mainApp;


	}

	public void handleAddElement() {

		if (newTissage.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de valeur");
			alert.setHeaderText("Pas de valeur");
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (tissageService.validate(newTissage.getText())) {
			tissageService.saveOrUpdate(new Tissage(newTissage.getText()));
			newTissage.setText("");
			allTissages = tissageService.getAllObs();
			listTissages.setItems(allTissages);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Tissage déja existante");
			alert.setContentText("Ce tissage existe déjà");

			alert.showAndWait();
		}

	}

	public void handleSelectElement(String tissage) {
		this.editedTissage = tissage;
		this.editTissage.setText(tissage);
		this.editTissage.setDisable(false);
	}

	public void handleEditElement() {
		if (newTissage.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de valeur");
			alert.setHeaderText("Pas de valeur");
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (tissageService.validate(editTissage.getText())) {
			tissageService.saveOrUpdate(new Tissage(editTissage.getText()));
			editTissage.setText("");
			allTissages = tissageService.getAllObs();
			listTissages.setItems(allTissages);

			this.editTissage.setDisable(true);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Matière déja existante");
			alert.setContentText("Cette matière existe déjà");

			alert.showAndWait();
		}
	}

	public void handleSuppressElement() {
		tissageService.delete(editTissage.getText());
		allTissages = tissageService.getAllObs();
		listTissages.setItems(allTissages);
		this.editedTissage = null;
		this.editTissage.setText("");
		this.editTissage.setDisable(true);
		this.editerButton.setDisable(true);
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
