package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class FournitureSearchController implements IController {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	private static final String CHOIX = "Choix";
	private FournitureSpecification specification;

	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXButton typeButton;
	@FXML
	public Label typeLbl;
	@FXML
	public JFXButton tissageButton;
	@FXML
	public JFXTextField descriptionField;
	@FXML
	public JFXTextField lieuDachatField;
	@FXML
	public JFXCheckBox margeCbx;
	@FXML
	public JFXCheckBox margeSecCbx;
	@FXML
	public JFXTextField margeField;
	@FXML
	public JFXTextField margeSecField;
	@FXML
	public JFXComboBox<String> typeField;
	@FXML
	public JFXTextField dimSecMin;
	@FXML
	private JFXComboBox<String> uniteSecCombo;
	@FXML
	public JFXTextField dimSecMax;
	@FXML
	public JFXCheckBox longueurUtilisableCBox;
	@FXML
	public JFXTextField dimPrimMin;
	@FXML
	public JFXTextField dimPrimMax;
	@FXML
	private JFXComboBox<String> unitePrimCombo;
	@FXML
	public JFXCheckBox archiveTrue;
	@FXML
	public JFXCheckBox archiveFalse;

	private final RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private final TypeFournitureService typeFournitureService;
	private List<String> typeValuesSelected = new ArrayList<>();


	public FournitureSearchController(TypeFournitureService typeFournitureService, RootController root) {
		this.typeFournitureService = typeFournitureService;
		this.root = root;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		CustomSpinner.setLongSpinner(dimPrimMax);
		CustomSpinner.setLongSpinner(dimPrimMin);
		CustomSpinner.setLongSpinner(dimSecMax);
		CustomSpinner.setLongSpinner(dimSecMin);
		CustomSpinner.setSpinner(margeField);
		CustomSpinner.setSpinner(margeSecField);

		margeCbx.selectedProperty().addListener((observable, oldValue, newValue) -> margeField.setDisable(!newValue));
		margeSecCbx.selectedProperty().addListener((observable, oldValue, newValue) -> margeSecField.setDisable(!newValue));

		typeLbl.setText(AUCUN_FILTRE);

		FxUtils.setToggleColor(margeCbx,	margeSecCbx,	longueurUtilisableCBox);
		setData(data);

	}

	private void setData(FxData data) {
		if (data != null && data.getSpecification() != null
				&& data.getSpecification() instanceof FournitureSpecification) {

			margeField.setText("0");
			margeSecField.setText("0");
			margeCbx.setSelected(false);
			margeSecCbx.setSelected(false);

			specification = (FournitureSpecification) data.getSpecification();

			List<String> types = null;
			if (specification.getType() != null) {
				types = specification.getType().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
			}
			FxUtils.setSelection(types, typeValuesSelected, typeLbl);
			
			if (specification.getDescription() != null
					&& Strings.isNotBlank(specification.getDescription().getContainsMultiple())) {
				descriptionField.setText(specification.getDescription().getContainsMultiple());
			}

			FxUtils.setTextFieldFromCharacterSearch(referenceField, specification.getReference());

			  FxUtils.setTextFieldMaxFromNumericSearch(dimPrimMax,
			  specification.getQuantite());
			  FxUtils.setTextFieldMinFromNumericSearch(dimPrimMin,
			  specification.getQuantite());
			  FxUtils.setTextFieldMaxFromNumericSearch(dimSecMax,
			  specification.getQuantiteSecondaire());
			  FxUtils.setTextFieldMinFromNumericSearch(dimSecMin,
			  specification.getQuantiteSecondaire());

			  List<String> typesSearch = null;
				if (specification.getType() != null) {
					typesSearch = specification.getType().stream().map(AbstractSimpleValueEntity::getValue)
							.collect(Collectors.toList());
				}
				FxUtils.setSelection(typesSearch, typeValuesSelected, typeLbl);


		} else {
			specification = new FournitureSpecification();
			typeLbl.setText(AUCUN_FILTRE);
			typeValuesSelected = new ArrayList<>();
			dimPrimMax.setText(Strings.EMPTY);
			dimSecMax.setText(Strings.EMPTY);
			descriptionField.setText(Strings.EMPTY);

		}
	}

	@FXML
	private void initialize() {

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleCancel() {
		FxData data = new FxData();
		data.setSpecification(new TissuSpecification());
		setData(data);
	}

	@FXML
	private void handleOk() {

		List<TypeFourniture> type = typeFournitureService.findTypeFourniture(typeValuesSelected);

		Unite unitePrim = Unite.getEnum(unitePrimCombo.getValue());

		int marge = margeField.isDisable() ? 0 : FxUtils.intFromJFXTextField(margeField);
		int margeSec = margeSecField.isDisable() ? 0 : FxUtils.intFromJFXTextField(margeSecField);

		NumericSearch<Float> dimPrimTemp = FxUtils.setNumericFloatSearch(
				Unite.convertir(FxUtils.floatFromJFXTextField(dimPrimMin), unitePrim),
				Unite.convertir(FxUtils.floatFromJFXTextField(dimPrimMax), unitePrim), marge);

		NumericSearch<Float> dimPrim = null;
		NumericSearch<Float> dimPrimDispo = null;

		if (longueurUtilisableCBox.isSelected()) {
			dimPrim = dimPrimTemp;
		} else {
			dimPrimDispo = dimPrimTemp;
		}

		Unite uniteSec = Unite.getEnum(uniteSecCombo.getValue());

		NumericSearch<Float> dimSec = FxUtils.setNumericFloatSearch(
				Unite.convertir(FxUtils.floatFromJFXTextField(dimSecMin), uniteSec),
				Unite.convertir(FxUtils.floatFromJFXTextField(dimSecMax), uniteSec), margeSec);

		CharacterSearch description = FxUtils.textFieldToCharacterSearchMultiple(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		specification = FournitureSpecification.builder().reference(reference).description(description).type(type)
				.quantite(dimPrim).quantiteDisponible(dimPrimDispo).quantiteSecondaire(dimSec).build();

		root.displayTissu(specification);
	}

	@FXML
	private void utilisableHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Quantité utilisable",
				"Définit si la recherche doit porter sur la quantité de fourniture en stock (\"Utilisable\" décoché) ou sur "
						+ "la quantité de fourniture disponible (\"Utilisable\" coché)");
	}
	
	@FXML
	private void choiceType() {
		FxUtils.setSelectionFromChoiceBoxModale(typeFournitureService.getAllValues(), typeValuesSelected, typeLbl);
	}

}
