package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class PatronSearchController implements IController {

	private static final String AUCUN_FILTRE = "Aucun filtre";
	public static final String CHOIX = "Choix";

	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXTextField marqueField;
	@FXML
	public JFXTextField modeleField;
	@FXML
	public JFXTextField typeVetementField;
	@FXML
	public JFXTextField descriptionField;
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
	public JFXCheckBox lourdCBox;
	@FXML
	public JFXCheckBox moyenCBox;
	@FXML
	public JFXCheckBox legerCBox;
	@FXML
	public JFXCheckBox ncCBox;
	@FXML
	public JFXTextField longueurFieldMax;
	@FXML
	public JFXTextField laizeFieldMax;

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private MatiereService matiereService;
	private TissageService tissageService;
	private UserPrefService userPrefService;

	private List<String> tissageValuesSelected;
	private List<String> typeValuesSelected;
	private List<String> matiereValuesSelected;

	private int margeHauteLeger;
	private int margeBasseMoyen;
	private int margeHauteMoyen;
	private int margeBasseLourd;
	private DecimalFormat df = new DecimalFormat("#.##");

	public PatronSearchController(RootController root, MatiereService matiereService, TissageService tissageService,
			UserPrefService userPrefService) {
		this.root = root;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.userPrefService = userPrefService;

	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;

		if (data != null && data.getSpecification() != null && data.getSpecification() instanceof PatronSpecification) {
			PatronSpecification spec = (PatronSpecification) data.getSpecification();
			setTextFieldFromCharacterSearch(descriptionField, spec.getDescription());
			setTextFieldFromCharacterSearch(typeVetementField, spec.getTypeVetement());
			setTextFieldFromCharacterSearch(marqueField, spec.getMarque());
			setTextFieldFromCharacterSearch(modeleField, spec.getModele());

		} else {

			typeLbl.setText(AUCUN_FILTRE);
			matiereLbl.setText(AUCUN_FILTRE);
			tissageLbl.setText(AUCUN_FILTRE);

			lourdCBox.setSelected(true);
			moyenCBox.setSelected(true);
			legerCBox.setSelected(true);
			ncCBox.setSelected(true);
		}

		UserPref pref = userPrefService.getUser();

		margeHauteLeger = Math.round(pref.getMinPoidsMoyen() + pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
		margeBasseMoyen = Math.round(pref.getMinPoidsMoyen() - pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
		margeHauteMoyen = Math.round(pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());
		margeBasseLourd = Math.round(pref.getMaxPoidsMoyen() - pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());

	}

	private void setTextFieldFromCharacterSearch(JFXTextField field, CharacterSearch charSearch) {
		if (charSearch != null && Strings.isNotBlank(charSearch.getContains())) {
			field.setText(charSearch.getContains());
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

		CharacterSearch description = FxUtils.textFieldToCharacterSearch(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		CharacterSearch marque = FxUtils.textFieldToCharacterSearch(marqueField);

		CharacterSearch modele = FxUtils.textFieldToCharacterSearch(modeleField);

		CharacterSearch typeVetement = FxUtils.textFieldToCharacterSearch(typeVetementField);

		NumericSearch<Integer> longueur = FxUtils.textFieldToMaxNumericSearch(longueurFieldMax);

		PatronSpecification specification = PatronSpecification.builder().description(description).reference(reference)
				.marque(marque).modele(modele).typeVetement(typeVetement).longueur(longueur).build();

		root.displayPatrons(specification);
	}

	@FXML
	private void lourdAction() {

	}

	@FXML
	private void moyenAction() {

	}

	@FXML
	private void legerAction() {

	}

	@FXML
	private void choiceType() {
		List<String> values = typeValuesSelected == null ? TypeTissuEnum.labels() : typeValuesSelected;
		getSelectionFromChoiceBoxModale(values, typeValuesSelected, typeLbl);
	}

	@FXML
	private void choiceMatiere() {
		List<String> values = matiereValuesSelected == null ? matiereService.getAllValues() : matiereValuesSelected;
		getSelectionFromChoiceBoxModale(values, matiereValuesSelected, matiereLbl);
	}

	private void getSelectionFromChoiceBoxModale(List<String> values, List<String> selectionDestination, Label lbl) {
		FxData data = new FxData();
		data.setListValues(values);
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, CHOIX);
		if (result != null) {
			if (selectionDestination == null){
				selectionDestination = new ArrayList<>();
			} else{
				selectionDestination.clear();
			}
			selectionDestination.addAll(result.getListValues());
			lbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), AUCUN_FILTRE));
		}
	}

	@FXML
	private void choiceTissage() {
		List<String> values = tissageValuesSelected == null ? tissageService.getAllValues() : tissageValuesSelected;
		getSelectionFromChoiceBoxModale(values, tissageValuesSelected, tissageLbl);
	}

	@FXML
	private void poidsHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
				"Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à "
						+ df.format(margeHauteLeger) + ", Moyen = entre " + df.format(margeBasseMoyen) + " et "
						+ df.format(margeHauteMoyen) + ", Lourd = supérieur à " + df.format(margeBasseLourd));
	}

}
