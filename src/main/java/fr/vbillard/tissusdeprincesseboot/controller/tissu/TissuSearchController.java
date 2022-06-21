package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.fxCustomElement.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.RowConstraints;

@Component
public class TissuSearchController implements IController {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	public static final String CHOIX = "Choix";
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

	private List<String> tissageValuesSelected;
	private List<String> typeValuesSelected;
	private List<String> matiereValuesSelected;

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
		specification = new TissuSpecification();

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

	}

	@FXML
	private void initialize() {

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		List<Matiere> matieres = null;
		if (matiereValuesSelected != null && !matiereValuesSelected.isEmpty()) {
			matieres = new ArrayList<>();
			for (String s : matiereValuesSelected) {
				matieres.add(matiereService.findMatiere(s));
			}
		}

		List<Tissage> tissages = null;
		if (tissageValuesSelected != null && !tissageValuesSelected.isEmpty()) {
			tissages = new ArrayList<>();
			for (String s : tissageValuesSelected) {
				tissages.add(tissageService.findTissage(s));
			}
		}

		List<TypeTissuEnum> types = null;
		if (typeValuesSelected != null && !typeValuesSelected.isEmpty()) {
			types = new ArrayList<>();
			for (String s : typeValuesSelected) {
				types.add(TypeTissuEnum.getEnum(s));
			}
		}

		NumericSearch<Integer> longueurSearch = setNumericSearch(longueurFieldMin, longueurFieldMax);

		NumericSearch<Integer> laizeSearch = setNumericSearch(laizeFieldMin, laizeFieldMax);

		CharacterSearch description = null;
		if (!descriptionField.getText().isEmpty()) {
			description = new CharacterSearch();
			description.setContains(descriptionField.getText());
		}

		CharacterSearch reference = null;
		if (!referenceField.getText().isEmpty()) {
			reference = new CharacterSearch();
			reference.setContains(referenceField.getText());
		}

		UserPref pref = prefService.getUser();

		margeHauteLeger = Math.round(pref.getMinPoidsMoyen() + pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
		margeBasseMoyen = Math.round(pref.getMinPoidsMoyen() - pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
		margeHauteMoyen = Math.round(pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());
		margeBasseLourd = Math.round(pref.getMaxPoidsMoyen() - pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());

		NumericSearch<Integer> poidsSearch = null;
		if (!lourdCBox.isSelected()) {
			poidsSearch = new NumericSearch<>(null);
			if (moyenCBox.isSelected()) {
				poidsSearch.setLessThanEqual(margeHauteMoyen);
			} else if (legerCBox.isSelected()) {
				poidsSearch.setLessThanEqual(margeHauteLeger);

			}
		}
		if (!legerCBox.isSelected()) {
			if (poidsSearch == null) {
				poidsSearch = new NumericSearch<>(null);
			}
			if (moyenCBox.isSelected()) {
				poidsSearch.setGreaterThanEqual(margeBasseMoyen);
			} else if (lourdCBox.isSelected()) {
				poidsSearch.setGreaterThanEqual(margeBasseLourd);
			}
		}
		if (ncCBox.isSelected()) {
			// TODO vérifier que NC marche en plus d'une sélection ?
			if (poidsSearch == null) {
				poidsSearch = new NumericSearch<>(null);
			}
			poidsSearch.setEquals(0);
		}

		Boolean decati = null;
		if (decatiTrue.isSelected()) {
			decati = true;
		} else if (decatiFalse.isSelected()) {
			decati = false;
		}

		Boolean chuteOuCoupon = null;
		if (chute.isSelected()) {
			chuteOuCoupon = true;
		} else if (coupon.isSelected()) {
			chuteOuCoupon = false;
		}

		specification = TissuSpecification.builder().reference(reference).description(description).chute(chuteOuCoupon)
				.decati(decati).laize(laizeSearch).poids(poidsSearch).longueur(longueurSearch).typeTissu(types)
				.matieres(matieres).tissages(tissages).build();

		root.displayTissu(specification);
	}

	private NumericSearch<Integer> setNumericSearch(JFXTextField min, JFXTextField max) {

		int minValue = getIntFromJFXTextField(min);
		int maxValue = getIntFromJFXTextField(max);

		if (minValue < 0 || maxValue < 0 || (maxValue != 0 && minValue >= maxValue)) {
			throw new IllegalData(
					"Les valeurs ne doivent pas être négatives. La valeur minimale doit être strictement inférieure à la valeur maximale");
		}

		NumericSearch<Integer> search = null;
		if (!max.getText().isEmpty()) {
			int value = Integer.parseInt(max.getText());
			if (value > 0) {
				search = new NumericSearch<>(null);
				search.setLessThanEqual(value);
			}

		}

		if (!min.getText().isEmpty()) {
			int value = Integer.parseInt(min.getText());
			if (value > 0) {
				if (search == null) {
					search = new NumericSearch<>(null);
				}
				search.setGreaterThanEqual(value);
			}
		}
		return search;
	}

	private int getIntFromJFXTextField(JFXTextField field) {
		int value = 0;
		if (!field.getText().isEmpty()) {
			value = Integer.parseInt(field.getText());
		}
		return value;
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
		FxData data = new FxData();
		data.setListValues(TypeTissuEnum.labels());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, CHOIX);
		if (result != null) {
			typeValuesSelected = result.getListValues();
			typeLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), AUCUN_FILTRE));
		}
	}

	@FXML
	private void choiceMatiere() {
		FxData data = new FxData();
		data.setListValues(matiereService.getAllValues());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, CHOIX);
		matiereValuesSelected = result.getListValues();
		matiereLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), AUCUN_FILTRE));
	}

	@FXML
	private void choiceTissage() {
		FxData data = new FxData();
		data.setListValues(tissageService.getAllValues());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, CHOIX);
		tissageValuesSelected = result.getListValues();
		tissageLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), AUCUN_FILTRE));
	}

}
