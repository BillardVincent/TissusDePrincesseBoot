package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
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

	private MatiereService matiereService;
	private TissageService tissageService;
	private UserPrefService userPrefService;

	private List<String> tissageValuesSelected = new ArrayList();
	private List<String> typeValuesSelected = new ArrayList();
	private List<String> matiereValuesSelected = new ArrayList();

	private int margeHauteLeger;
	private int margeBasseMoyen;
	private int margeHauteMoyen;
	private int margeBasseLourd;
	private DecimalFormat df = new DecimalFormat("#.##");

	private boolean okClicked = false;

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
			FxUtils.setTextFieldFromCharacterSearch(descriptionField, spec.getDescription());
			FxUtils.setTextFieldFromCharacterSearch(typeVetementField, spec.getTypeVetement());
			FxUtils.setTextFieldFromCharacterSearch(marqueField, spec.getMarque());
			FxUtils.setTextFieldFromCharacterSearch(modeleField, spec.getModele());

			List<String> types = null;
			if (spec.getTypeTissu() != null) {
				types = spec.getTypeTissu().stream().map(m -> m.getLabel()).collect(Collectors.toList());
			}
			FxUtils.setSelection(types, typeValuesSelected, typeLbl);

			List<String> matieres = null;
			if (spec.getMatieres() != null) {
				matieres = spec.getMatieres().stream().map(m -> m.getValue()).collect(Collectors.toList());
			}
			FxUtils.setSelection(matieres, matiereValuesSelected, matiereLbl);

			List<String> tissages = null;
			if (spec.getTissages() != null) {
				tissages = spec.getTissages().stream().map(m -> m.getValue()).collect(Collectors.toList());
			}
			FxUtils.setSelection(tissages, tissageValuesSelected, tissageLbl);

			FxUtils.setTextFieldMaxFromNumericSearch(longueurFieldMax, spec.getLongueur());
			FxUtils.setTextFieldMaxFromNumericSearch(laizeFieldMax, spec.getLaize());

			if (spec.getPoids() != null && !spec.getPoids().isEmpty()) {
				lourdCBox.setSelected(spec.getPoids().contains(GammePoids.LOURD));
				moyenCBox.setSelected(spec.getPoids().contains(GammePoids.MOYEN));
				legerCBox.setSelected(spec.getPoids().contains(GammePoids.LEGER));
				ncCBox.setSelected(spec.getPoids().contains(GammePoids.NON_RENSEIGNE));
			}

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

		CharacterSearch description = FxUtils.textFieldToCharacterSearch(descriptionField);

		CharacterSearch reference = FxUtils.textFieldToCharacterSearch(referenceField);

		CharacterSearch marque = FxUtils.textFieldToCharacterSearch(marqueField);

		CharacterSearch modele = FxUtils.textFieldToCharacterSearch(modeleField);

		CharacterSearch typeVetement = FxUtils.textFieldToCharacterSearch(typeVetementField);

		NumericSearch<Integer> longueur = FxUtils.textFieldToMaxNumericSearch(longueurFieldMax);

		PatronSpecification specification = PatronSpecification.builder().description(description).reference(reference)
				.matieres(matieres).typeTissu(types).tissages(tissages).marque(marque).modele(modele)
				.typeVetement(typeVetement).longueur(longueur).build();

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

	@FXML
	private void poidsHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
				"Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à "
						+ df.format(margeHauteLeger) + ", Moyen = entre " + df.format(margeBasseMoyen) + " et "
						+ df.format(margeHauteMoyen) + ", Lourd = supérieur à " + df.format(margeBasseLourd));
	}

}
