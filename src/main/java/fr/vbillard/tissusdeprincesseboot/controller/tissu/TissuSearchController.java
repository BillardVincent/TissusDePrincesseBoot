package fr.vbillard.tissusdeprincesseboot.controller.tissu;

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
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

@Component
public class TissuSearchController implements IController {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	private static final String CHOIX = "Choix";
	private TissuSpecification specification;

	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXButton typeButton;
	@FXML
	public Label typeLbl;
	@FXML
	public JFXButton tissageButton;
	@FXML
	public Label tissageLbl;
	@FXML
	public Label matiereLbl;
	@FXML
	public JFXTextField descriptionField;
	@FXML
	public JFXTextField lieuDachatField;
	@FXML
	public JFXCheckBox lourdCBox;
	@FXML
	public JFXCheckBox moyenCBox;
	@FXML
	public JFXCheckBox legerCBox;
	@FXML
	public JFXCheckBox ncCBox;
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
	public JFXRadioButton decatiTrue;
	@FXML
	public JFXRadioButton decatiFalse;
	@FXML
	public JFXRadioButton decatiAll;
	@FXML
	public JFXRadioButton chute;
	@FXML
	public JFXRadioButton coupon;
	@FXML
	public JFXRadioButton chuteEtCoupon;
	@FXML
	public JFXCheckBox archiveTrue;
	@FXML
	public JFXCheckBox archiveFalse;

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private MatiereService matiereService;
	private TissageService tissageService;

	private final ToggleGroup decatiGroup = new ToggleGroup();
	private final ToggleGroup chuteGroup = new ToggleGroup();

	private List<String> tissageValuesSelected = new ArrayList<>();
	private List<String> typeValuesSelected = new ArrayList<>();
	private List<String> matiereValuesSelected = new ArrayList<>();

	private int margeHauteLeger;
	private int margeBasseMoyen;
	private int margeHauteMoyen;
	private int margeBasseLourd;
	private DecimalFormat df = new DecimalFormat("#.##");

	private UserPrefService prefService;

	public TissuSearchController(MatiereService matiereService, TissageService tissageService, RootController root,
			UserPrefService prefService) {
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.root = root;
		this.prefService = prefService;

		UserPref pref = prefService.getUser();

		margeHauteLeger = pref.margeHauteLeger();
		margeBasseMoyen = pref.margeBasseMoyen();
		margeHauteMoyen = pref.margeHauteMoyen();
		margeBasseLourd = pref.margeBasseLourd();
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		CustomSpinner.setSpinner(longueurFieldMax);
		CustomSpinner.setSpinner(longueurFieldMin);
		CustomSpinner.setSpinner(laizeFieldMax);
		CustomSpinner.setSpinner(laizeFieldMin);

		decatiTrue.setToggleGroup(decatiGroup);
		decatiFalse.setToggleGroup(decatiGroup);
		decatiAll.setToggleGroup(decatiGroup);
		decatiAll.setSelected(true);

		chute.setToggleGroup(chuteGroup);
		coupon.setToggleGroup(chuteGroup);
		chuteEtCoupon.setToggleGroup(chuteGroup);
		chuteEtCoupon.setSelected(true);

		typeLbl.setText(AUCUN_FILTRE);
		matiereLbl.setText(AUCUN_FILTRE);
		tissageLbl.setText(AUCUN_FILTRE);

		lourdCBox.setSelected(true);
		moyenCBox.setSelected(true);
		legerCBox.setSelected(true);

		// TODO mettre en palce NC
		ncCBox.setSelected(false);
		ncCBox.setVisible(false);

		setData(data);

	}

	private void setData(FxData data) {
		if (data != null && data.getSpecification() != null && data.getSpecification() instanceof TissuSpecification) {

			specification = (TissuSpecification) data.getSpecification();

			if (specification.getDescription() != null
					&& Strings.isNotBlank(specification.getDescription().getContainsMultiple())) {
				descriptionField.setText(specification.getDescription().getContainsMultiple());
			}

			FxUtils.setTextFieldFromCharacterSearch(referenceField, specification.getReference());

			FxUtils.setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
			FxUtils.setTextFieldMinFromNumericSearch(longueurFieldMin, specification.getLongueur());
			FxUtils.setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());
			FxUtils.setTextFieldMinFromNumericSearch(laizeFieldMin, specification.getLaize());

			List<String> types = null;
			if (specification.getTypeTissu() != null) {
				types = specification.getTypeTissu().stream().map(TypeTissuEnum::getLabel).collect(Collectors.toList());
			}
			FxUtils.setSelection(types, typeValuesSelected, typeLbl);

			List<String> matieres = null;
			if (specification.getMatieres() != null) {
				matieres = specification.getMatieres().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
			}
			FxUtils.setSelection(matieres, matiereValuesSelected, matiereLbl);

			List<String> tissages = null;
			if (specification.getTissages() != null) {
				tissages = specification.getTissages().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
			}
			FxUtils.setSelection(tissages, tissageValuesSelected, tissageLbl);

			FxUtils.setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
			FxUtils.setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());

			setPoidsFromSpec();

		} else {
			specification = new TissuSpecification();

			typeLbl.setText(AUCUN_FILTRE);
			typeValuesSelected = new ArrayList<>();
			matiereLbl.setText(AUCUN_FILTRE);
			matiereValuesSelected = new ArrayList<>();
			tissageLbl.setText(AUCUN_FILTRE);
			tissageValuesSelected = new ArrayList<>();

			longueurFieldMax.setText(Strings.EMPTY);
			laizeFieldMax.setText(Strings.EMPTY);
			descriptionField.setText(Strings.EMPTY);

			lourdCBox.setSelected(true);
			moyenCBox.setSelected(true);
			legerCBox.setSelected(true);
			ncCBox.setSelected(true);
		}
	}

	private void setPoidsFromSpec() {
		if (specification.getPoidsNC() != null) {
			ncCBox.setSelected(specification.getPoidsNC());
		} else {
			ncCBox.setSelected(specification.getPoids() == null);
		}

		if (specification.getPoids() != null) {

			Integer min = specification.getPoids().getGreaterThanEqual();
			Integer max = specification.getPoids().getLessThanEqual();

			boolean minIsNullOrZero = min == null || min == 0;

			if (minIsNullOrZero && (max == null || max == 0)) {
				lourdCBox.setSelected(true);
				moyenCBox.setSelected(true);
				legerCBox.setSelected(true);
			} else {

					legerCBox.setSelected(minIsNullOrZero);
					moyenCBox.setSelected(min != null && min >= margeBasseMoyen && max != null && max > margeHauteLeger);
					lourdCBox.setSelected(max != null && max > margeHauteMoyen);
			}
		} else {
			lourdCBox.setSelected(true);
			moyenCBox.setSelected(true);
			legerCBox.setSelected(true);
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
		List<Matiere> matieres = matiereService.findMatiere(matiereValuesSelected);

		List<Tissage> tissages = tissageService.tissageValuesSelected(tissageValuesSelected);

		List<TypeTissuEnum> types = null;
		if (typeValuesSelected != null && !typeValuesSelected.isEmpty()) {
			types = new ArrayList<>();
			for (String s : typeValuesSelected) {
				types.add(TypeTissuEnum.getEnum(s));
			}
		}

		NumericSearch<Integer> longueurSearch = FxUtils.setNumericSearch(longueurFieldMin, longueurFieldMax);

		NumericSearch<Integer> laizeSearch = FxUtils.setNumericSearch(laizeFieldMin, laizeFieldMax);

		CharacterSearch description = FxUtils.textFieldToCharacterSearchMultiple(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		NumericSearch<Integer> poidsSearch = FxUtils.NumericSearch(lourdCBox, moyenCBox, legerCBox,
				prefService.getUser());

		Boolean poidsSearchNc = null; // ncCBox.isSelected();

		Boolean decati = FxUtils.getBooleanFromRadioButtons(decatiTrue, decatiFalse, decatiAll);

		Boolean chuteOuCoupon = FxUtils.getBooleanFromRadioButtons(chute, coupon, chuteEtCoupon);

		specification = TissuSpecification.builder().reference(reference).description(description).chute(chuteOuCoupon)
				.decati(decati).laize(laizeSearch).poids(poidsSearch).poidsNC(poidsSearchNc).longueur(longueurSearch)
				.typeTissu(types).matieres(matieres).tissages(tissages).build();

		root.displayTissu(specification);
	}

	@FXML
	private void lourdAction() {
		if (!moyenCBox.isSelected() && legerCBox.isSelected()) {
			moyenCBox.setSelected(true);
		}
	}

	@FXML
	private void moyenAction() {
		if (!moyenCBox.isSelected() && lourdCBox.isSelected() && legerCBox.isSelected()) {
			lourdCBox.setSelected(false);
		}
	}

	@FXML
	private void legerAction() {
		if (!moyenCBox.isSelected() && lourdCBox.isSelected()) {
			moyenCBox.setSelected(true);
		}
	}

	@FXML
	private void poidsHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
				"Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à "
						+ df.format(margeHauteLeger) + ", Moyen = entre " + df.format(margeBasseMoyen) + " et "
						+ df.format(margeHauteMoyen) + ", Lourd = supérieur à " + df.format(margeBasseLourd));
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

	@FXML
	private void choiceMatiere() {
		FxUtils.setSelectionFromChoiceBoxModale(matiereService.getAllValues(), matiereValuesSelected, matiereLbl);
	}

	@FXML
	private void choiceTissage() {
		FxUtils.setSelectionFromChoiceBoxModale(tissageService.getAllValues(), tissageValuesSelected, tissageLbl);
	}

}
