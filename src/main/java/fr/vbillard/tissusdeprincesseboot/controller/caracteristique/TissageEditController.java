package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

@Component
public class TissageEditController extends AbstractCaracteristiqueController {

	@FXML
	private JFXListView<String> listTissages;

	@FXML
	private JFXTextField newTissage;
	@FXML
	private JFXTextField editTissage;

	private TissageService tissageService;

	private String editedTissage;

	private ObservableList<String> allTissages;

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

	public TissageEditController(TissageService tissageService, StageInitializer mainApp) {
		this.tissageService = tissageService;
		this.mainApp = mainApp;
	}

	public void handleSelectElement(String tissage) {
		this.editedTissage = tissage;
		this.editTissage.setText(tissage);
		this.editTissage.setDisable(false);
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

	@Override
	protected boolean fieldSaveEmpty() {
		return StringUtils.isEmpty(newTissage.getText());
	}

	@Override
	protected boolean fieldEditEmpty() {
		return StringUtils.isEmpty(editTissage.getText());
	}

	@Override
	protected boolean validateSave() {
		return tissageService.validate(newTissage.getText());
	}

	@Override
	protected boolean validateEdit() {
		return tissageService.validate(editTissage.getText());
	}

	@Override
	protected String fieldAlreadyExists() {
		return "Tissage déjà existant";
	}

	@Override
	protected String thisField() {
		return "Ce tissage";
	}

	@Override
	protected void save() {
		tissageService.saveOrUpdate(new Tissage(newTissage.getText()));
		newTissage.setText("");
		allTissages = tissageService.getAllObs();
		listTissages.setItems(allTissages);
	}

	@Override
	protected void edit() {
		Tissage t = tissageService.findTissage(editedTissage);
		t.setValue(editTissage.getText());
		tissageService.saveOrUpdate(t);
		editTissage.setText("");
		allTissages = tissageService.getAllObs();
		listTissages.setItems(allTissages);

		this.editTissage.setDisable(true);
	}

}
