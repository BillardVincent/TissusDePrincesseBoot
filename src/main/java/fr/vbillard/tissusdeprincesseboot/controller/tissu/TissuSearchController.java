package fr.vbillard.tissusdeprincesseboot.controller.tissu;

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
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.RowConstraints;

@Component
public class TissuSearchController implements IController {

	TissuSpecification specification;
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
	public JFXButton matiereButton;
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

	public RowConstraints ancienneValeurRow;
	public RowConstraints consommeRow;

	private RootController root;
	private StageInitializer initializer;

	private boolean okClicked = false;

	private ModelMapper mapper;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissuService tissuService;

	private final ToggleGroup decatiGroup = new ToggleGroup();
	private final ToggleGroup chuteGroup = new ToggleGroup();

	private List<String> tissageValuesSelected;
	private List<String> typeValuesSelected;
	private List<String> matiereValuesSelected;

	public TissuSearchController(ModelMapper mapper, TissuService tissuService, MatiereService matiereService,
			TissageService tissageService, RootController root) {
		this.mapper = mapper;
		this.tissuService = tissuService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.root = root;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		specification = new TissuSpecification();

		decatiTrue.setToggleGroup(decatiGroup);
		decatiFalse.setToggleGroup(decatiGroup);
		decatiAll.setToggleGroup(decatiGroup);

		chute.setToggleGroup(chuteGroup);
		coupon.setToggleGroup(chuteGroup);
		chuteEtCoupon.setToggleGroup(chuteGroup);

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
		specification = TissuSpecification.builder().matieres(matieres).build();

		Page<Tissu> tissu = tissuService.getAll(specification, PageRequest.of(0, 10));
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
	private void ncAction() {

	}

	@FXML
	private void poidsHelp() {
		DevInProgressService.notImplemented(initializer);
	}

	@FXML
	private void utilisableHelp() {
		DevInProgressService.notImplemented(initializer);

	}

	@FXML
	private void choiceType() {
		FxData data = new FxData();
		data.setListValues(TypeTissuEnum.labels());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, "Choix");
		typeValuesSelected = result.getListValues();
		typeLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), "Aucun filtre"));

	}

	@FXML
	private void choiceMatiere() {
		FxData data = new FxData();
		data.setListValues(matiereService.getAllValues());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, "Choix");
		matiereValuesSelected = result.getListValues();
		matiereLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), "Aucun filtre"));
	}

	@FXML
	private void choiceTissage() {
		FxData data = new FxData();
		data.setListValues(tissageService.getAllValues());
		FxData result = initializer.displayModale(PathEnum.CHECKBOX_CHOICE, data, "Choix");
		tissageValuesSelected = result.getListValues();
		tissageLbl.setText(StringUtils.defaultIfEmpty(FxUtils.joinValues(result), "Aucun filtre"));
	}

}
