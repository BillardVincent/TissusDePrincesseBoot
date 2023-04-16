package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	public JFXTextField poidsField;
	@FXML
	public JFXComboBox<String> unitePoidsField;
	@FXML
	public JFXTextField laizeField;
	@FXML
	public JFXTextField longueurField;
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

	private RootController root;
	private StageInitializer initializer;

	private TissuDto tissu;
	private boolean okClicked = false;

	private ModelMapper mapper;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissuService tissuService;
	private TissuPictureHelper pictureHelper;
	private ConstantesMetier constantesMetier;

	public TissuEditController(TissuPictureHelper pictureHelper, ModelMapper mapper, TissuService tissuService,
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
					UnitePoids.NON_RENSEIGNE, false, "", null, false, false), TissuDto.class);
		}

		longueurField.setText(FxUtils.safePropertyToString(tissu.getLongueurProperty()));
		laizeField.setText(FxUtils.safePropertyToString(tissu.getLaizeProperty()));
		poidsField.setText(FxUtils.safePropertyToString(tissu.getPoidseProperty()));
		referenceField.setText(FxUtils.safePropertyToString(tissu.getReferenceProperty()));
		descriptionField.setText(FxUtils.safePropertyToString(tissu.getDescriptionProperty()));
		decatiField.setSelected(tissu.getDecatiProperty() != null && tissu.isDecati());
		lieuDachatField.setText(FxUtils.safePropertyToString(tissu.getLieuAchatProperty()));
		chuteField.setSelected(tissu.getChuteProperty() != null && tissu.isChute());

		unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
		unitePoidsField.setValue(
				tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

		typeField.setItems(FXCollections.observableArrayList(TypeTissuEnum.labels()));
		typeField.setValue(
				tissu.getUnitePoidsProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissu.getTypeTissu());

		matiereField.setItems(FXCollections.observableArrayList(matiereService.getAllMatieresValues()));
		matiereField.setValue(FxUtils.safePropertyToString(tissu.getMatiereProperty()));

		tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
		tissageField.setValue(FxUtils.safePropertyToString(tissu.getTissageProperty()));

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

		CustomSpinner.setSpinner(longueurField);
		CustomSpinner.setSpinner(laizeField);
		CustomSpinner.setSpinner(poidsField);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {

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
		matiereField.setValue(FxUtils.safePropertyToString(tissu.getMatiereProperty()));

	}

	@FXML
	private void handleAddTissage() {
		initializer.displayModale(PathEnum.TISSAGE, null, "Tissage");

		tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
		tissageField.setValue(FxUtils.safePropertyToString(tissu.getTissageProperty()));
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (referenceField.getText() == null || referenceField.getText().length() == 0) {
			errorMessage += "Référence non renseignée.\n";
		}
		if (matiereField.getValue() == null) {
			errorMessage += "Matière non renseignée.\n";
		}
		if (unitePoidsField.getValue() == null) {
			errorMessage += "Unité de poids non renseignée.\n";
		}
		if (poidsField.getText() == null) {
			errorMessage += "Poids non renseigné.\n";
		}
		if (tissageField.getValue() == null) {
			errorMessage += "Tissage non renseigné.\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			// alert.initOwner(dialogStage);
			alert.setTitle("Valeurs incorrectes");
			alert.setHeaderText("Merci de renseigner les champs suivants:");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
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
		if (isInputValid()) {
			setTissuFromFields();

			pictureHelper.addPictureLocal(tissu);
		}
	}

	@FXML
	private void addPictureWeb() {
		if (isInputValid()) {
			setTissuFromFields();
			pictureHelper.addPictureWeb(tissu);
		}
	}

	@FXML
	private void addPictureFromClipboard(){
		if (isInputValid()) {
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
