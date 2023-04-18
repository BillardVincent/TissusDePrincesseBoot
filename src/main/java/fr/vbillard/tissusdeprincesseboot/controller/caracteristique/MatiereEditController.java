package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

@Component
public class MatiereEditController extends AbstractCaracteristiqueController {

	@FXML
	private JFXListView<String> listMatieres;
	@FXML
	private JFXTextField newMatiere;
	@FXML
	private JFXTextField editMatiere;

	private MatiereService matiereService;

	private String editedMatiere;

	private ObservableList<String> allMatieres;

	public MatiereEditController(MatiereService matiereService, StageInitializer mainApp) {
		this.matiereService = matiereService;
		this.mainApp = mainApp;
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

	public void handleSuppressElement() {
		matiereService.delete(editMatiere.getText());
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
		this.editedMatiere = null;
		this.editMatiere.setText("");
		this.editMatiere.setDisable(true);
		this.editerButton.setDisable(true);
	}

	public void handleSelectElement(String matiere) {
		this.editedMatiere = matiere;
		this.editMatiere.setText(matiere);
		this.editMatiere.setDisable(false);
		this.editerButton.setDisable(false);

	}

	@Override
	protected boolean fieldSaveEmpty() {
		return StringUtils.isEmpty(newMatiere.getText());
	}

	@Override
	protected boolean fieldEditEmpty() {
		return StringUtils.isEmpty(editMatiere.getText());
	}

	@Override
	protected boolean validateSave() {
		return matiereService.validate(newMatiere.getText());
	}

	@Override
	protected boolean validateEdit() {
		return matiereService.validate(editMatiere.getText());
	}

	@Override
	protected String fieldAlreadyExists() {
		return "Matière déja existante";
	}

	@Override
	protected String thisField() {
		return "Cette Matière";
	}

	@Override
	protected void save() {
		matiereService.saveOrUpdate(new Matiere(newMatiere.getText()));
		newMatiere.setText(Strings.EMPTY);
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
	}

	@Override
	protected void edit() {
		Matiere m = matiereService.findMatiere(editedMatiere);
		m.setValue(editMatiere.getText());
		matiereService.saveOrUpdate(m);
		editMatiere.setText("");
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
		this.editMatiere.setDisable(true);
	}

}
