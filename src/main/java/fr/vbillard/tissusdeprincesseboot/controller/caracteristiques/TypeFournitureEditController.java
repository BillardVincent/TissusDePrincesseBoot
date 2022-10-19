package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

@Component
public class TypeFournitureEditController extends AbstractCaracteristiqueController {

	@FXML
	private JFXListView<String> listType;
	@FXML
	private JFXTextField newType;
	@FXML
	private JFXTextField editType;

	private TypeFournitureService typeFournitureService;

	private String editedType;

	private ObservableList<String> allTypes;

	public TypeFournitureEditController(TypeFournitureService typeFournitureService, StageInitializer mainApp) {
		this.typeFournitureService = typeFournitureService;
		this.mainApp = mainApp;
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		listType.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

		this.editType.setDisable(true);
		this.editerButton.setDisable(true);

	}

	public void handleSuppressElement() {
		typeFournitureService.delete(editType.getText());
		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		this.editedType = null;
		this.editType.setText("");
		this.editType.setDisable(true);
		this.editerButton.setDisable(true);
	}

	public void handleSelectElement(String type) {
		this.editedType = type;
		this.editType.setText(type);
		this.editType.setDisable(false);
		this.editerButton.setDisable(false);

	}

	@Override
	protected boolean fieldSaveEmpty() {
		return StringUtils.isEmpty(newType.getText());
	}

	@Override
	protected boolean fieldEditEmpty() {
		return StringUtils.isEmpty(editType.getText());
	}

	@Override
	protected boolean validateSave() {
		return typeFournitureService.validate(newType.getText());
	}

	@Override
	protected boolean validateEdit() {
		return typeFournitureService.validate(editType.getText());
	}

	@Override
	protected String fieldAlreadyExists() {
		return "Type de fourniture déja existant";
	}

	@Override
	protected String thisField() {
		return "Ce type de fourniture";
	}

	@Override
	protected void save() {
		typeFournitureService.saveOrUpdate(new TypeFourniture(newType.getText()));
		newType.setText(Strings.EMPTY);
		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
	}

	@Override
	protected void edit() {
		TypeFourniture m = typeFournitureService.findTypeFourniture(editedType);
		m.setValue(editType.getText());
		typeFournitureService.saveOrUpdate(m);
		editType.setText("");
		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		this.editType.setDisable(true);
	}

}
