package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.pictureHelper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;

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

	public RowConstraints ancienneValeurRow;
	public RowConstraints consommeRow;

	private int longueur;
	private int laize;
	private int poids;

	private RootController root;
	private StageInitializer initializer;

	private TissuDto tissu;
	private boolean edit;
	private boolean okClicked = false;

	private ModelMapper mapper;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissuService tissuService;
	private TissuPictureHelper pictureHelper;

	public TissuEditController(ImageService imageService, TissuPictureHelper pictureHelper, ModelMapper mapper,
			TissuService tissuService, MatiereService matiereService, TissageService tissageService,
			RootController root) {
		this.mapper = mapper;
		this.tissuService = tissuService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.pictureHelper = pictureHelper;
		this.root = root;
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
					UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
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
		typeField.setValue(tissu.getUnitePoidsProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissu.getTypeTissu());

		matiereField.setItems(FXCollections.observableArrayList(matiereService.getAll().stream()
				.map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
		matiereField.setValue(FxUtils.safePropertyToString(tissu.getMatiereProperty()));

		tissageField.setItems(FXCollections.observableArrayList(tissageService.getAll().stream()
				.map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
		tissageField.setValue(FxUtils.safePropertyToString(tissu.getTissageProperty()));

		pictureHelper.setPane(imagePane, tissu);

	}

	@FXML
	private void initialize() {

		addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
		generateReferenceButton.setGraphic(magicIcon);
		generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

		IntegerSpinner.setSpinner(longueurField);
		IntegerSpinner.setSpinner(laizeField);
		IntegerSpinner.setSpinner(poidsField);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			if (tissu.getChuteProperty() == null) {
				tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, TypeTissuEnum.NON_RENSEIGNE, 0,
						UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
			}
			tissu.setReference(referenceField.getText());
			tissu.setLongueur(longueur);
			tissu.setLaize(Integer.valueOf(laizeField.getText()));
			tissu.setDescription(descriptionField.getText());
			tissu.setMatiere(matiereField.getValue());
			tissu.setTypeTissu(typeField.getValue());
			tissu.setPoids(Integer.valueOf(poidsField.getText()));
			tissu.setUnitePoids(unitePoidsField.getValue());
			tissu.setDecati(decatiField.isSelected());
			tissu.setLieuAchat(lieuDachatField.getText());
			tissu.setChute(chuteField.isSelected());
			tissu.setTissage(tissageField.getValue());

			tissu = tissuService.saveOrUpdate(tissu);
			okClicked = true;

			root.displayTissusDetails(tissu);
		}
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
		root.displayMatiereEdit();
	}

	@FXML
	private void handleAddTissage() {
		root.displayTissageEdit();
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
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		sb.append(typeField.getValue().trim().isEmpty() ? "X" : typeField.getValue().toUpperCase().charAt(0))
				.append(matiereField.getValue().trim().isEmpty() ? "X"
						: matiereField.getValue().toUpperCase().charAt(0))
				.append(tissageField.getValue().trim().isEmpty() ? "X"
						: tissageField.getValue().toUpperCase().charAt(0))
				.append("-");
		if (Boolean.parseBoolean(chuteField.getText()))
			sb.append("cp-");
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

	public void setButton() {
		int height = edit ? 30 : 0;
		ancienneValeurRow.setMaxHeight(height);
		ancienneValeurRow.setMinHeight(height);
		ancienneValeurRow.setPrefHeight(height);
		consommeRow.setMaxHeight(height);
		consommeRow.setMinHeight(height);
		consommeRow.setPrefHeight(height);
		ancienneValeurLabel.setVisible(edit);
		consommeLabel.setVisible(edit);
		ancienneValeurInfo.setVisible(edit);
		consommeIndo.setVisible(edit);

		laizeField.setDisable(edit);
		poidsField.setDisable(edit);
		referenceField.setDisable(edit);
		descriptionField.setDisable(edit);
		decatiField.setDisable(edit);
		lieuDachatField.setDisable(edit);
		chuteField.setDisable(edit);
	}

	@FXML
	private void addPicture() {
		tissuService.saveOrUpdate(tissu);
		pictureHelper.addPictureLocal();
	}

	@FXML
	private void addPictureWeb() {
		tissuService.saveOrUpdate(tissu);
		pictureHelper.addPictureWeb();
	}

	@FXML
	private void pictureExpend() {

	}

}
