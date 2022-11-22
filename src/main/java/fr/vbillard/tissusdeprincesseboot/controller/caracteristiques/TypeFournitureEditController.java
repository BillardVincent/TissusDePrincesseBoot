package fr.vbillard.tissusdeprincesseboot.controller.caracteristiques;

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
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@Component
public class TypeFournitureEditController implements IModalController {

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
	private Label uniteConsPrimLbl;
	@FXML
	private JFXComboBox<String> unitePrimCombo;
	@FXML
	private JFXTextField intitulePrimField;
	@FXML
	private JFXCheckBox uniteSecondaireCheckBx;
	@FXML
	private JFXComboBox<String> dimensionSecCombo;
	@FXML
	private Label uniteConsSecLbl;
	@FXML
	private JFXComboBox<String> uniteSecCombo;
	@FXML
	private JFXTextField intituleSecField;
	@FXML
	private GridPane secondaryGrid;
	@FXML
	private Label intitulePrimAsterix;
	@FXML
	private Label intituleSecAsterix;
	@FXML
	private Label dimensionSecAsterix;
	@FXML
	private JFXButton deselectionnerButton;

	private Stage dialogStage;
	private boolean okClicked = false;
	private StageInitializer mainApp;
	private TypeFournitureService typeFournitureService;
	private TypeFourniture typeFourniture;

	private ObservableList<String> allTypes;

	private static final String PAS_DE_VALEUR = "Champs obligatoire à remplir";

	public TypeFournitureEditController(TypeFournitureService typeFournitureService, StageInitializer mainApp) {
		this.typeFournitureService = typeFournitureService;
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

		typeFourniture = null;
		
		listType.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));
		
		dimensionPrimCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (dimensionPrimCombo.getValue() != null
					&& !dimensionPrimCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
				unitePrimCombo.setDisable(false);
				uniteConsPrimLbl.setDisable(false);
				unitePrimCombo.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionPrimCombo.getValue())))));
				unitePrimCombo.setValue(typeFourniture == null || typeFourniture.getUnitePrincipaleConseillee() == null
						? DimensionEnum.NON_RENSEIGNE.getLabel()
						: typeFourniture.getUnitePrincipaleConseillee().getLabel());
			} else {
				
				unitePrimCombo.setDisable(true);
				uniteConsPrimLbl.setDisable(true);
				unitePrimCombo.setItems(null);
				unitePrimCombo.setValue(null);
				
			}
		});

		dimensionSecCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (dimensionSecCombo.getValue() != null
					&& !dimensionSecCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
				uniteSecCombo.setDisable(false);
				uniteConsSecLbl.setDisable(false);
				uniteSecCombo.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionSecCombo.getValue())))));

				uniteSecCombo.setValue(typeFourniture == null || typeFourniture.getUniteSecondaireConseillee() == null
						? DimensionEnum.NON_RENSEIGNE.getLabel()
						: typeFourniture.getUniteSecondaireConseillee().getLabel());
			} else {
				uniteSecCombo.setDisable(true);
				uniteConsSecLbl.setDisable(true);
				uniteSecCombo.setItems(null);
				uniteSecCombo.setValue(null);
			}
		});

		resetField();

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

		uniteSecondaireCheckBx
				.setSelected(typeFourniture != null && StringUtils.isNotEmpty(typeFourniture.getIntituleSecondaire()));
		resetField();

	}

	protected void save() {
		if (typeFourniture == null) {
			typeFourniture = new TypeFourniture();
		}

		if (typeFourniture != null && typeFourniture.getId() != 0) {
			typeFournitureService.checkIfTypeFournitureIsUsed(typeFourniture);
		}

		typeFourniture.setValue(nomField.getText());

		typeFourniture.setDimensionPrincipale(DimensionEnum.valueOf(dimensionPrimCombo.getValue()));

		if (!dimensionPrimCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
			typeFourniture.setIntitulePrincipale(intitulePrimField.getText());
			typeFourniture.setUnitePrincipaleConseillee(Unite.valueOf(unitePrimCombo.getValue()));

		}


		if (!secondaryGrid.isDisabled()) {
			typeFourniture.setDimensionSecondaire(DimensionEnum.valueOf(dimensionSecCombo.getValue()));
			if (!dimensionSecCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
				typeFourniture.setIntituleSecondaire(intituleSecField.getText());
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
			alert.setContentText("Veuillez renseigner une valeur dans tous les champs obligatoires");
			alert.showAndWait();
		} else if (typeFournitureService.validate(nomField.getText(), typeFourniture.getId())) {
			save();
		} else {
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

		if (dimensionPrimCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
			isValid = isValid && StringUtils.isNoneEmpty(intitulePrimField.getText());
		}

		if (dimensionSecCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
			isValid = isValid && StringUtils.isNoneEmpty(intituleSecField.getText());
		}

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
		deselectionnerButton.setVisible(typeFourniture != null);
		


		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);
		ajouterButton.setText("Ajouter");

		nomField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getValue());

		intitulePrimField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getIntitulePrincipale());

		dimensionPrimCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

		dimensionPrimCombo.setValue(typeFourniture == null || typeFourniture.getDimensionPrincipale() == null
				? DimensionEnum.NON_RENSEIGNE.getLabel()
				: typeFourniture.getDimensionPrincipale().getLabel());

		setSecondPane();
	}

	private void setSecondPane() {
		if (uniteSecondaireCheckBx.isSelected()) {
			secondaryGrid.setDisable(false);
			
			intituleSecField.setText(typeFourniture == null ? Strings.EMPTY : typeFourniture.getIntituleSecondaire());

			dimensionSecCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

			dimensionSecCombo.setValue(typeFourniture == null || typeFourniture.getDimensionSecondaire() == null
					? DimensionEnum.NON_RENSEIGNE.getLabel()
					: typeFourniture.getDimensionSecondaire().getLabel());

			if (dimensionSecCombo.getValue() != null) {

				uniteSecCombo.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionSecCombo.getValue())))));

				uniteSecCombo.setValue(typeFourniture == null || typeFourniture.getUniteSecondaireConseillee() == null
						? DimensionEnum.NON_RENSEIGNE.getLabel()
						: typeFourniture.getUniteSecondaireConseillee().getLabel());
			} else {
				unitePrimCombo.setDisable(true);
			}
		} else {
			secondaryGrid.setDisable(true);
			intituleSecField.setText(Strings.EMPTY);

			dimensionSecCombo.setItems(null);

			dimensionSecCombo.setValue(null);

			uniteSecCombo.setItems(null);

			uniteSecCombo.setValue(null);
		}
	}

	@FXML
	public void handleUniteSecondaireCheck() {
		setSecondPane();
	}
	
	@FXML
	public void handleHelpPrim() {
		ShowAlert.information(dialogStage, "Info", "Dimension principale", "La dimension principale caractérise un type de fourniture. Elle est consommée en fonction de l'utilisation de la fourniture dans les projets. Exemple : la longueur d'un ruban / le nombre de boutons. N/A défini une fourniture dont la consommation de sera pas suivie");
	}
	
	@FXML
	public void handleHelpSec() {
		ShowAlert.information(dialogStage, "Info", "Dimension secondaire", "La dimension secondaire caractérise un type de fourniture. Elle est informative et n'est pas consommée en fonction de l'utilisation de la fourniture dans les projets. Exemple : la largeur d'un ruban, le diametre de boutons");
	}
	
	@FXML
	public void handleDeselectionner() {
        listType.getSelectionModel().clearSelection();

	}

}
