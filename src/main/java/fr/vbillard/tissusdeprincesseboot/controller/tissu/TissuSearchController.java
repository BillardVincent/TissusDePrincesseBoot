package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
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

	private List<String> tissageValuesSelected = new ArrayList();
	private List<String> typeValuesSelected = new ArrayList();
	private List<String> matiereValuesSelected = new ArrayList();

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

	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		IntegerSpinner.setSpinner(longueurFieldMax);
		IntegerSpinner.setSpinner(longueurFieldMin);
		IntegerSpinner.setSpinner(laizeFieldMax);
		IntegerSpinner.setSpinner(laizeFieldMin);

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
		ncCBox.setSelected(true);

		if (data != null && data.getSpecification() != null && data.getSpecification() instanceof TissuSpecification) {

			specification = (TissuSpecification) data.getSpecification();

			FxUtils.setTextFieldFromCharacterSearch(descriptionField, specification.getDescription());
			FxUtils.setTextFieldFromCharacterSearch(referenceField, specification.getReference());

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

			setPoidsFromSpec();

		} else {
			specification = new TissuSpecification();
		}

	}

	private void setPoidsFromSpec() {
		if (specification.getPoids() != null) {

			UserPref pref = prefService.getUser();

			int margeHauteLeger = Math
					.round(pref.getMinPoidsMoyen() + pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
			int margeBasseMoyen = Math
					.round(pref.getMinPoidsMoyen() - pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
			int margeHauteMoyen = Math
					.round(pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());
			int margeBasseLourd = Math
					.round(pref.getMaxPoidsMoyen() - pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());

			Integer min = specification.getPoids().getGreaterThanEqual();
			Integer max = specification.getPoids().getLessThanEqual();

			// TODO
			lourdCBox.setSelected(specification.getPoids().contains(GammePoids.LOURD));
			moyenCBox.setSelected(specification.getPoids().contains(GammePoids.MOYEN));
			legerCBox.setSelected(specification.getPoids().contains(GammePoids.LEGER));
			ncCBox.setSelected(specification.getPoids().contains(GammePoids.NON_RENSEIGNE));
		}
	}

	@FXML
	private void initialize() {

	}

	public boolean isOkClicked() {
		return okClicked;
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

		CharacterSearch description = FxUtils.textFieldToCharacterSearch(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		NumericSearch<Integer> poidsSearch = FxUtils.NumericSearch(lourdCBox, moyenCBox, legerCBox, ncCBox,
				prefService.getUser());

		Boolean decati = FxUtils.getBooleanFromRadioButtons(decatiTrue, decatiFalse, decatiAll);

		Boolean chuteOuCoupon = FxUtils.getBooleanFromRadioButtons(chute, coupon, chuteEtCoupon);

		specification = TissuSpecification.builder().reference(reference).description(description).chute(chuteOuCoupon)
				.decati(decati).laize(laizeSearch).poids(poidsSearch).longueur(longueurSearch).typeTissu(types)
				.matieres(matieres).tissages(tissages).build();

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
