package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class MatiereEditController implements IModalController {

	public static final String PAS_DE_VALEUR = "Pas de valeur";
	@FXML
	private JFXListView<String> listMatieres;
	@FXML
	private JFXTextField newMatiere;
	@FXML
	private JFXTextField editMatiere;

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

	private MatiereService matiereService;
	private StageInitializer mainApp;

	private String editedMatiere;

	private ObservableList<String> allMatieres;

	public MatiereEditController(MatiereService matiereService) {
		this.matiereService = matiereService;
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
		listMatieres.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

		this.editMatiere.setDisable(true);
		this.editerButton.setDisable(true);

	}

	public void setData(StageInitializer mainApp) {
		this.mainApp = mainApp;
	}

	public void handleAddElement() {

		if (newMatiere.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle(PAS_DE_VALEUR);
			alert.setHeaderText(PAS_DE_VALEUR);
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (matiereService.validate(newMatiere.getText())) {
			matiereService.saveOrUpdate(new Matiere(newMatiere.getText()));
			newMatiere.setText(Strings.EMPTY);
			allMatieres = matiereService.getAllMatieresValues();
			listMatieres.setItems(allMatieres);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Matière déja existante");
			alert.setContentText("Cette matière existe déjà");

			alert.showAndWait();
		}

	}

	public void handleSelectElement(String matiere) {
		this.editedMatiere = matiere;
		this.editMatiere.setText(matiere);
		this.editMatiere.setDisable(false);
		this.editerButton.setDisable(false);

	}

	public void handleEditElement() {
		if (editMatiere.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle(PAS_DE_VALEUR);
			alert.setHeaderText(PAS_DE_VALEUR);
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (matiereService.validate(editMatiere.getText())) {
			Matiere m = matiereService.findMatiere(editedMatiere);
			m.setValue(editMatiere.getText());
			matiereService.saveOrUpdate(m);
			editMatiere.setText("");
			allMatieres = matiereService.getAllMatieresValues();
			listMatieres.setItems(allMatieres);
			this.editMatiere.setDisable(true);

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
		matiereService.delete(editMatiere.getText());
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
		this.editedMatiere = null;
		this.editMatiere.setText("");
		this.editMatiere.setDisable(true);
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
