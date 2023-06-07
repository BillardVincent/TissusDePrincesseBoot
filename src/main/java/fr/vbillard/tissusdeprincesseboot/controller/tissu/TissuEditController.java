package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils.areValidatorsValid;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.validators.NonNullValidator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

@Component
public class TissuEditController implements IController {
	@FXML
	public JFXToggleButton decatiField;
	@FXML
	public JFXTextField descriptionField;
	@FXML
	public JFXTextField lieuDachatField;
	@FXML
	public IntegerSpinner poidsField;
	@FXML
	public JFXComboBox<String> unitePoidsField;
	@FXML
	public IntegerSpinner laizeField;
	@FXML
	public IntegerSpinner longueurField;
	@FXML
	public JFXToggleButton chuteField;
	@FXML
	public JFXComboBox<String> typeField;
	@FXML
	public JFXComboBox<String> matiereField;
	@FXML
	public JFXButton addMatiereButton;
	@FXML
	public JFXComboBox<String> tissageField;
	@FXML
	public JFXButton addTissageButton;
	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXButton generateReferenceButton;
	@FXML
	public Label ancienneValeurLabel;
	@FXML
	public Label ancienneValeurInfo;
	@FXML
	public Label consommeLabel;
	@FXML
	public Label consommeIndo;
	@FXML
	public ImageView imagePane;
	@FXML
	public JFXButton addPictureWebBtn;
	@FXML
	public JFXButton pictureExpendBtn;
	@FXML
	public JFXButton addPictureBtn;
	@FXML
	public Label imageNotSaved;
	@FXML
	public JFXButton addPictureClipboardBtn;
	@FXML
	public JFXButton archiverBtn;

	private final RootController root;
	private StageInitializer initializer;

	private TissuDto tissu;
	private boolean okClicked = false;

	private final MapperService mapper;
	private final MatiereService matiereService;
	private final TissageService tissageService;
	private final TissuService tissuService;
	private final TissuPictureHelper pictureHelper;
	private final ConstantesMetier constantesMetier;

	private Validator[] validators;

	public TissuEditController(TissuPictureHelper pictureHelper, MapperService mapper, TissuService tissuService,
			MatiereService matiereService, TissageService tissageService, RootController root,
			ConstantesMetier constantesMetier) {
		this.mapper = mapper;
		this.tissuService = tissuService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.pictureHelper = pictureHelper;
		this.root = root;
		this.constantesMetier = constantesMetier;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;

		if (data == null || data.getTissu() == null) {
			throw new IllegalData();
		}
		tissu = data.getTissu();
		if (tissu == null || tissu.getChuteProperty() == null) {
			tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, TypeTissuEnum.NON_RENSEIGNE, 0,
					UnitePoids.NON_RENSEIGNE, false, "", null, false, false));
		}

		longueurField.setText(safePropertyToString(tissu.getLongueurProperty()));
		laizeField.setText(safePropertyToString(tissu.getLaizeProperty()));
		poidsField.setText(safePropertyToString(tissu.getPoidseProperty()));
		referenceField.setText(safePropertyToString(tissu.getReferenceProperty()));
		descriptionField.setText(safePropertyToString(tissu.getDescriptionProperty()));
		decatiField.setSelected(tissu.getDecatiProperty() != null && tissu.isDecati());
		lieuDachatField.setText(safePropertyToString(tissu.getLieuAchatProperty()));
		chuteField.setSelected(tissu.getChuteProperty() != null && tissu.isChute());

		unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
		unitePoidsField.setValue(
				tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

		typeField.setItems(FXCollections.observableArrayList(TypeTissuEnum.labels()));
		typeField.setValue(
				tissu.getUnitePoidsProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissu.getTypeTissu());

		matiereField.setItems(FXCollections.observableArrayList(matiereService.getAllMatieresValues()));
		matiereField.setValue(safePropertyToString(tissu.getMatiereProperty()));

		tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
		tissageField.setValue(safePropertyToString(tissu.getTissageProperty()));

		pictureHelper.setPane(imagePane, tissu);
		boolean tissuIsNew = tissu.getId() == 0;
		addPictureWebBtn.setDisable(tissuIsNew);
		pictureExpendBtn.setDisable(tissuIsNew);
		addPictureBtn.setDisable(tissuIsNew);
		imageNotSaved.setVisible(tissuIsNew);
		addPictureClipboardBtn.setDisable(tissuIsNew);
		setBoutonArchiver();
	}

	@FXML
	private void initialize() {
		addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
		generateReferenceButton.setGraphic(magicIcon);
		generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

		validators = new Validator[] {new NonNullValidator<>(referenceField, "référence"),
				new NonNullValidator<>(matiereField, "matière"),
				new NonNullValidator<>(unitePoidsField, "unité de poids"),
				new NonNullValidator<>(poidsField, "poids"),
				new NonNullValidator<>(typeField, "type")};
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (areValidatorsValid(initializer, validators)) {

			setTissuFromFields();
			okClicked = true;
			root.displayTissusDetails(tissu);
		}
	}

	private void setTissuFromFields() {
		tissu.setReference(referenceField.getText());
		tissu.setLongueur(Integer.parseInt(longueurField.getText()));
		tissu.setLaize(Integer.parseInt(laizeField.getText()));
		tissu.setDescription(descriptionField.getText());
		tissu.setMatiere(matiereField.getValue());
		tissu.setTypeTissu(typeField.getValue());
		tissu.setPoids(Integer.parseInt(poidsField.getText()));
		tissu.setUnitePoids(unitePoidsField.getValue());
		tissu.setDecati(decatiField.isSelected());
		tissu.setLieuAchat(lieuDachatField.getText());
		tissu.setChute(chuteField.isSelected());
		tissu.setTissage(tissageField.getValue());

		tissu = tissuService.saveOrUpdate(tissu);
	}

	@FXML
	private void handleCancel() {
		if (tissu.getId() != 0) {
			root.displayTissusDetails(tissu);
		} else {
			root.displayTissus();
		}
	}

	@FXML
	private void handleAddMatiere() {
		initializer.displayModale(PathEnum.MATIERE, null, "Matière");

		matiereField.setItems(FXCollections.observableArrayList(matiereService.getAllMatieresValues()));
		matiereField.setValue(safePropertyToString(tissu.getMatiereProperty()));
	}

	@FXML
	private void handleAddTissage() {
		initializer.displayModale(PathEnum.TISSAGE, null, "Tissage");

		tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
		tissageField.setValue(safePropertyToString(tissu.getTissageProperty()));
	}

	private String getFirstCharOrX(JFXComboBox<String> field) {
		if (field == null || Strings.isBlank(field.getValue())) {
			return "X";
		}
		return field.getValue().toUpperCase().substring(0, 1);
	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		sb.append(getFirstCharOrX(typeField)).append(getFirstCharOrX(matiereField))
				.append(getFirstCharOrX(tissageField)).append("-");
		if (chuteField.isSelected()) {
			sb.append("cp-");
		}
		boolean ref = true;
		int refNb = 0;
		while (ref) {
			refNb++;
			ref = tissuService.existByReference(sb.toString() + refNb);
		}
		referenceField.setText(sb.append(refNb).toString());
	}

	private void nextTissu(TissuDto tissu, Map<TissuDto, Integer> mapTissu) {
		mapTissu.remove(tissu);
	}

	@FXML
	private void addPicture() {
		if (areValidatorsValid(initializer, validators)) {
			setTissuFromFields();

			pictureHelper.addPictureLocal(tissu);
		}
	}

	@FXML
	private void addPictureWeb() {
		if (areValidatorsValid(initializer, validators)) {
			setTissuFromFields();
			pictureHelper.addPictureWeb(tissu);
		}
	}

	@FXML
	private void addPictureFromClipboard(){
		if (areValidatorsValid(initializer, validators)) {
			setTissuFromFields();
			pictureHelper.addPictureClipBoard(tissu);
		}
	}

	@FXML
	private void pictureExpend() {
		DevInProgressService.notImplemented();
	}

	@FXML
	public void poidsHelp() {
		ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids",
				"Un poids renseigné en g/m sera converti en g/m² lors de l'enregistrement (laize doit être renseigné). Un "
						+ "tissu léger sera entre  0 et " + constantesMetier.getMinPoidsMoyen()
						+ "g/m², un tissu moyen entre " + constantesMetier.getMinPoidsMoyen() + "g/m² et "
						+ constantesMetier.getMaxPoidsMoyen() + "g/m², un tissu lourd aura un poids supérieur à "
						+ constantesMetier.getMaxPoidsMoyen() + "g/m².");
	}

	@FXML
	public void archiver(){
		tissu.setArchived(!tissu.isArchived());
		tissu = tissuService.saveOrUpdate(tissu);
		setBoutonArchiver();
	}

	private void setBoutonArchiver() {
		archiverBtn.setText(tissu.isArchived() ? "Désarchiver" :"Archiver");
	}

}
