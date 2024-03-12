package fr.vbillard.tissusdeprincesseboot.controller.caracteristique;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
	@FXML
	private FontAwesomeIconView dimensionLockIcn;

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
		ajouterButton.setDisable(true);

		FxUtils.setToggleColor(uniteSecondaireCheckBx);

		listType.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

		nomField.textProperty().addListener((observable, oldValue, newValue) ->
				ajouterButton.setDisable(Strings.isEmpty(newValue)));

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
				dimensionSecAsterix.setVisible(true);
				intituleSecAsterix.setVisible(true);

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

				dimensionSecAsterix.setVisible(false);
				intituleSecAsterix.setVisible(false);
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

		uniteSecondaireCheckBx
				.setSelected(typeFourniture != null && StringUtils.isNotEmpty(typeFourniture.getIntituleSecondaire()));
		resetField();

	}

	protected void save() {
		if (typeFourniture == null) {
			typeFourniture = new TypeFourniture();
		}

		if (typeFourniture.getId() == 0 || !typeFournitureService.checkIfTypeFournitureIsUsed(typeFourniture)){
			typeFourniture.setDimensionPrincipale(DimensionEnum.getEnum(dimensionPrimCombo.getValue()));
		}

		typeFourniture.setValue(nomField.getText());

		if (!dimensionPrimCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
			typeFourniture.setIntitulePrincipale(intitulePrimField.getText());
			typeFourniture.setUnitePrincipaleConseillee(Unite.getEnum(unitePrimCombo.getValue()));
		}

		if (!secondaryGrid.isDisabled()) {
			typeFourniture.setDimensionSecondaire(DimensionEnum.getEnum(dimensionSecCombo.getValue()));
			if (!dimensionSecCombo.getValue().equals(DimensionEnum.NON_RENSEIGNE.getLabel())) {
				typeFourniture.setIntituleSecondaire(intituleSecField.getText());
				typeFourniture.setUniteSecondaireConseillee(Unite.getEnum(uniteSecCombo.getValue()));
			}
		}

		typeFournitureService.saveOrUpdate(typeFourniture);
		resetField();
	}

	public void handleSaveOrEdit() {

		if (!validateField()) {
			ShowAlert.warn(mainApp.getPrimaryStage(), PAS_DE_VALEUR, PAS_DE_VALEUR,
					"Veuillez renseigner une valeur dans tous les champs obligatoires");
		} else if (typeFournitureService.validate(nomField.getText(), typeFourniture)) {
			save();
			typeFourniture = null;
			listType.getSelectionModel().clearSelection();
		} else {
			ShowAlert.warn(mainApp.getPrimaryStage(), "Duplicat", "Type de fourniture déja existant"
					, "Ce type de fourniture existe déjà");

		}
	}

	private boolean validateField() {
		boolean isValid = nomField.getText() != null;

		if (DimensionEnum.NON_RENSEIGNE.getLabel().equals(dimensionPrimCombo.getValue())) {
			isValid = isValid && StringUtils.isNoneEmpty(intitulePrimField.getText());
		}
		if (DimensionEnum.NON_RENSEIGNE.getLabel().equals(dimensionSecCombo.getValue())) {
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
		supprimerButton.setVisible(typeFourniture != null);

		boolean typeIsNull = typeFourniture == null;
		boolean typeIsNew =  typeIsNull || typeFourniture.getId() == 0;
		boolean isUsed = !typeIsNew && typeFournitureService.checkIfTypeFournitureIsUsed(typeFourniture);

		dimensionLockIcn.setVisible(isUsed);
		dimensionPrimCombo.setDisable(isUsed);

		nomField.setText(typeIsNull ? Strings.EMPTY : typeFourniture.getValue());

		intitulePrimField.setText(typeIsNull ? Strings.EMPTY : typeFourniture.getIntitulePrincipale());

		dimensionPrimCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

		dimensionPrimCombo.setValue(typeIsNull || typeFourniture.getDimensionPrincipale() == null
				? DimensionEnum.NON_RENSEIGNE.getLabel()
				: typeFourniture.getDimensionPrincipale().getLabel());

			if (typeIsNew) {
				ajouterButton.setText("Ajouter");
			} else {
				ajouterButton.setText("Editer");
			}

		allTypes = typeFournitureService.getAllTypeFournituresValues();
		listType.setItems(allTypes);

		setSecondPane(typeIsNull);
	}

	private void setSecondPane(boolean typeIsNull) {
		if (uniteSecondaireCheckBx.isSelected()) {
			secondaryGrid.setDisable(false);
			
			intituleSecField.setText(typeIsNull ? Strings.EMPTY : typeFourniture.getIntituleSecondaire());

			dimensionSecCombo.setItems(FXCollections.observableArrayList(DimensionEnum.labels()));

			dimensionSecCombo.setValue(typeIsNull || typeFourniture.getDimensionSecondaire() == null
					? DimensionEnum.NON_RENSEIGNE.getLabel()
					: typeFourniture.getDimensionSecondaire().getLabel());

			if (dimensionSecCombo.getValue() != null) {

				uniteSecCombo.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(
						Objects.requireNonNull(DimensionEnum.getEnum(dimensionSecCombo.getValue())))));

				uniteSecCombo.setValue(typeIsNull || typeFourniture.getUniteSecondaireConseillee() == null
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

			dimensionSecAsterix.setVisible(false);
			intituleSecAsterix.setVisible(false);
		}
	}

	@FXML
	public void handleUniteSecondaireCheck() {
		setSecondPane(typeFourniture == null);
	}
	
	@FXML
	public void handleHelpPrim() {
		ShowAlert.information(dialogStage, "Info", "Dimension principale",
				"La dimension principale caractérise un type de fourniture.\n\n"
						+ "Elle est consommée en fonction de l'utilisation de la fourniture dans les projets.\n\n"
						+ "Exemple : la longueur d'un ruban / le nombre de boutons.\n\n"
						+ "N/A défini une fourniture dont la consommation de sera pas suivie");
	}
	
	@FXML
	public void handleHelpSec() {
		ShowAlert.information(dialogStage, "Info", "Dimension secondaire",
				"La dimension secondaire caractérise un type de fourniture.\n\nElle est informative et n'est pas "
						+ "consommée en fonction de l'utilisation de la fourniture dans les projets."
						+ "\n\nExemple : la largeur d'un ruban, le diamètre de boutons");
	}

	@FXML
	public void handleHelpLock() {
		ShowAlert.information(dialogStage, "Info", "Vérouillé",
				"Ce type est utilisé dans une fourniture ou un projet. Vous ne pouvez pas plus modifier sa dimension");
	}
	
	@FXML
	public void handleDeselectionner() {
		listType.getSelectionModel().clearSelection();
	}

}
