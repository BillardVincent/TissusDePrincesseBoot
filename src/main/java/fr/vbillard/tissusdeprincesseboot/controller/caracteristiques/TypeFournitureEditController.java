package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@Component
public class TypeFournitureEditController implements IModalController{

	@FXML
	private JFXButton ajouterButton;
	@FXML
	private JFXButton supprimerButton;
	@FXML
	private JFXButton fermerButton;
	@FXML
	private JFXTextField nomField;
	@FXML
	private JFXListView<String> listType;	
	@FXML
	private JFXComboBox<String> dimensionPrimCombo;
	@FXML
	private JFXComboBox<String> unitePrimCombo;
	@FXML
	private JFXTextField intitulePrimField;
	@FXML
	private JFXCheckBox uniteSecondaireCheckBx;
	@FXML
	private JFXComboBox<String> dimensionSecCombo;
	@FXML
	private JFXComboBox<String> uniteSecCombo;
	@FXML
	private JFXTextField intituleSecField;	
	@FXML
	private GridPane secondaryGrid;
	
	
	private Stage dialogStage;
	private boolean okClicked = false;
	private StageInitializer mainApp;
	private TypeFournitureService typeFournitureService;
	private TypeFourniture typeFourniture;

	private ObservableList<String> allTypes;

	private static final String PAS_DE_VALEUR = "Champs obligatoire à remplir";
	private static final String AUCUN = "Aucun";

	public TypeFournitureEditController(TypeFournitureService typeFournitureService, StageInitializer mainApp) {
		this.typeFournitureService = typeFournitureService;
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		resetField();

		dimensionPrimCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

		dimensionPrimCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (dimensionPrimCombo.getValue() != null && !dimensionPrimCombo.getValue().equals(AUCUN)){
				unitePrimCombo.setDisable(false);
				setComboBox(unitePrimCombo, Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionPrimCombo.getValue()))));
				unitePrimCombo.setValue(typeFourniture == null ||
						typeFourniture.getUnitePrincipaleConseillee() == null ?
						AUCUN : typeFourniture.getUnitePrincipaleConseillee().getLabel());
			} else {
				unitePrimCombo.setDisable(true);
			}
		});

		dimensionSecCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (dimensionSecCombo.getValue() != null && !dimensionSecCombo.getValue().equals(AUCUN)){
				uniteSecCombo.setDisable(false);
				setComboBox(uniteSecCombo, Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionSecCombo.getValue()))));
				uniteSecCombo.setValue(typeFourniture == null ||
						typeFourniture.getUniteSecondaireConseillee() == null ?
						AUCUN : typeFourniture.getUniteSecondaireConseillee().getLabel());
			} else {
				unitePrimCombo.setDisable(true);
			}
		});

		if (typeFourniture.getId() != 0){
			typeFournitureService.checkIfTypeFournitureIsUsed(typeFourniture);
		}
	}

	public void handleSuppressElement() {
		typeFournitureService.delete(typeFourniture.getId());
		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		typeFourniture = null;
		resetField();
	}

	public void handleSelectElement(String type) {
		typeFourniture = typeFournitureService.findTypeFourniture(type);
		ajouterButton.setText("Editer");
	}

	protected void save() {
		if (typeFourniture == null){
			typeFourniture = new TypeFourniture();
		}
		typeFourniture.setValue(nomField.getText());

		typeFourniture.setDimensionPrincipale(DimensionEnum.valueOf(dimensionPrimCombo.getValue()));
		if (!dimensionPrimCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())){
			typeFourniture.setIntitulePrincipale(intitulePrimField.getText());
		}
		if (!unitePrimCombo.getValue().equals(AUCUN)){
			typeFourniture.setUnitePrincipaleConseillee(Unite.valueOf(unitePrimCombo.getValue()));
		}

		if (!secondaryGrid.isDisabled()){
			typeFourniture.setDimensionSecondaire(DimensionEnum.valueOf(dimensionSecCombo.getValue()));
			if (!dimensionSecCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())){
				typeFourniture.setIntituleSecondaire(intituleSecField.getText());
			}
			if (!uniteSecCombo.getValue().equals(AUCUN)){
				typeFourniture.setUniteSecondaireConseillee(Unite.valueOf(uniteSecCombo.getValue()));
			}
		}

		typeFournitureService.saveOrUpdate(typeFourniture);
		resetField();
	}

	public void handleSaveOrEdit() {
		if (!validateField()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle(PAS_DE_VALEUR);
			alert.setHeaderText(PAS_DE_VALEUR);
			alert.setContentText("Veuillez remplir une valeur");
			alert.showAndWait();
		} else if (typeFournitureService.validate(nomField.getText(), typeFourniture.getId())) {
			save();
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Type de fourniture déja existant");
			alert.setContentText("Ce type de fourniture existe déjà");

			alert.showAndWait();
		}
	}

	private boolean validateField() {
		boolean isValid = nomField.getText() != null;


		//TODO
		
		return isValid;
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
	
	private void resetField() {
		listType.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		ajouterButton.setText("Ajouter");
		
		nomField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getValue());
		
		intitulePrimField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getIntitulePrincipale());

		dimensionPrimCombo.setValue(typeFourniture == null ||
				typeFourniture.getDimensionPrincipale() == null ?
						DimensionEnum.NON_RENSEIGNE.getLabel() : typeFourniture.getDimensionPrincipale().getLabel());

		boolean hasSecondary = typeFourniture != null && StringUtils.isNoneEmpty(typeFourniture.getIntituleSecondaire());
		
		uniteSecondaireCheckBx.setSelected(hasSecondary);
		
		if (hasSecondary) {
			
			intituleSecField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getIntituleSecondaire());
	
			dimensionSecCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

			dimensionSecCombo.setValue(typeFourniture == null ||
					typeFourniture.getDimensionSecondaire() == null ?
							DimensionEnum.NON_RENSEIGNE.getLabel() : typeFourniture.getDimensionSecondaire().getLabel());

			if (dimensionSecCombo.getValue() != null){
				setComboBox(uniteSecCombo, Unite.labels());
			uniteSecCombo.setValue(typeFourniture == null ||
					typeFourniture.getUniteSecondaireConseillee() == null ?
							AUCUN : typeFourniture.getUniteSecondaireConseillee().getLabel());
			} else {
				unitePrimCombo.setDisable(true);
			}
		} else {
			secondaryGrid.setDisable(true);
		}
	}

	private void setComboBox(JFXComboBox<String> comboBox, List<String> labels) {
		ObservableList<String> values =  FXCollections.observableArrayList(AUCUN);
		values.addAll(labels);
		comboBox.setItems(values);		
	}

}
