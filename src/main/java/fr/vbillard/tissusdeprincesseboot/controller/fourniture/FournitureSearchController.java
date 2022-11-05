package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

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
	public JFXTextField laizeFieldMin;
	@FXML
	public JFXTextField laizeFieldMax;
	@FXML
	public JFXCheckBox longueurUtilisableCBox;
	@FXML
	public JFXTextField longueurFieldMin;
	@FXML
	public JFXTextField longueurFieldMax;
	@FXML
	public JFXCheckBox archiveTrue;
	@FXML
	public JFXCheckBox archiveFalse;

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private TypeFournitureService typeFournitureService;

	private List<String> typeValuesSelected = new ArrayList();

	private int margeHauteLeger;
	private int margeBasseMoyen;
	private int margeHauteMoyen;
	private int margeBasseLourd;
	private DecimalFormat df = new DecimalFormat("#.##");

	private UserPrefService prefService;

	public FournitureSearchController(TypeFournitureService typeFournitureService, RootController root) {
		this.typeFournitureService = typeFournitureService;
		this.root = root;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		IntegerSpinner.setSpinner(longueurFieldMax);
		IntegerSpinner.setSpinner(longueurFieldMin);
		IntegerSpinner.setSpinner(laizeFieldMax);
		IntegerSpinner.setSpinner(laizeFieldMin);

		typeLbl.setText(AUCUN_FILTRE);


		setData(data);

	}

	private void setData(FxData data) {
		if (data != null && data.getSpecification() != null && data.getSpecification() instanceof FournitureSpecification) {

			specification = (FournitureSpecification) data.getSpecification();

			if (specification.getDescription() != null
					&& Strings.isNotBlank(specification.getDescription().getContainsMultiple())) {
				descriptionField.setText(specification.getDescription().getContainsMultiple());
			}

			FxUtils.setTextFieldFromCharacterSearch(referenceField, specification.getReference());

			/*
			FxUtils.setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
			FxUtils.setTextFieldMinFromNumericSearch(longueurFieldMin, specification.getLongueur());
			FxUtils.setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());
			FxUtils.setTextFieldMinFromNumericSearch(laizeFieldMin, specification.getLaize());

			List<String> types = null;
			if (specification.getTypeTissu() != null) {
				types = specification.getTypeTissu().stream().map(m -> m.getLabel()).collect(Collectors.toList());
			}
			FxUtils.setSelection(types, typeValuesSelected, typeLbl);

			List<String> matieres = null;
			if (specification.getMatieres() != null) {
				matieres = specification.getMatieres().stream().map(m -> m.getValue()).collect(Collectors.toList());
			}
			FxUtils.setSelection(matieres, matiereValuesSelected, matiereLbl);

			List<String> tissages = null;
			if (specification.getTissages() != null) {
				tissages = specification.getTissages().stream().map(m -> m.getValue()).collect(Collectors.toList());
			}
			FxUtils.setSelection(tissages, tissageValuesSelected, tissageLbl);

			FxUtils.setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
			FxUtils.setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());
			*/

		} else {
			specification = new FournitureSpecification();

			typeLbl.setText(AUCUN_FILTRE);
			typeValuesSelected = new ArrayList<String>();
			
			longueurFieldMax.setText(Strings.EMPTY);
			laizeFieldMax.setText(Strings.EMPTY);
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
		List<TypeFourniture> types = typeFournitureService.findTypeFourniture(typeValuesSelected);

		NumericSearch<Integer> longueurSearch = FxUtils.setNumericSearch(longueurFieldMin, longueurFieldMax);

		NumericSearch<Integer> laizeSearch = FxUtils.setNumericSearch(laizeFieldMin, laizeFieldMax);

		CharacterSearch description = FxUtils.textFieldToCharacterSearchMultiple(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		specification = FournitureSpecification.builder().reference(reference).description(description)
				.type(types).build();

		root.displayTissu(specification);
	}

	
	@FXML
	private void utilisableHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Tissu utilisable",
				"Définit si la recherche doit porter sur la quantité de tissu en stock (\"Utilisable\" décoché) ou sur la quantité de tissu disponible (\"Utilisable\" coché)");
	}

	@FXML
	private void choiceType() {
		FxUtils.setSelectionFromChoiceBoxModale(TypeTissuEnum.labels(), typeValuesSelected, typeLbl);
	}

}
