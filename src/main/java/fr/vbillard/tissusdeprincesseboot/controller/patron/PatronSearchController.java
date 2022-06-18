package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
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
	private PatronSpecification specification;

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

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private MatiereService matiereService;
	private TissageService tissageService;
	private ConstantesMetier constantesMetier;

	private List<String> tissageValuesSelected;
	private List<String> typeValuesSelected;
	private List<String> matiereValuesSelected;

	private int margeHauteLeger;
	private int margeBasseMoyen;
	private int margeHauteMoyen;
	private int margeBasseLourd;
	private DecimalFormat df = new DecimalFormat("#.##");

	public PatronSearchController(RootController root, MatiereService matiereService, TissageService tissageService,
			ConstantesMetier constantesMetier) {
		this.root = root;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.constantesMetier = constantesMetier;

	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		specification = new PatronSpecification();

		typeLbl.setText(AUCUN_FILTRE);
		matiereLbl.setText(AUCUN_FILTRE);
		tissageLbl.setText(AUCUN_FILTRE);

		margeHauteLeger = Math.round(constantesMetier.getMinPoidsMoyen()
				+ constantesMetier.getMinPoidsMoyen() * constantesMetier.getMargePoidsErreur());
		margeBasseMoyen = Math.round(constantesMetier.getMinPoidsMoyen()
				- constantesMetier.getMinPoidsMoyen() * constantesMetier.getMargePoidsErreur());
		margeHauteMoyen = Math.round(constantesMetier.getMaxPoidsMoyen()
				+ constantesMetier.getMaxPoidsMoyen() * constantesMetier.getMargePoidsErreur());
		margeBasseLourd = Math.round(constantesMetier.getMaxPoidsMoyen()
				- constantesMetier.getMaxPoidsMoyen() * constantesMetier.getMargePoidsErreur());

	}

	@FXML
	private void initialize() {

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		PatronSpecification specification = PatronSpecification.builder().build();

		root.displayTissu(specification);
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

	@FXML
	private void poidsHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
				"Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à "
						+ df.format(margeHauteLeger) + ", Moyen = entre " + df.format(margeBasseMoyen) + " et "
						+ df.format(margeHauteMoyen) + ", Lourd = supérieur à " + df.format(margeBasseLourd));
	}

}
