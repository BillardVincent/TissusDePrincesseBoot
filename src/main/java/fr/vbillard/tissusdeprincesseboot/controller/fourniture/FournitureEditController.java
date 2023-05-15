package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.FloatSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.FourniturePictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

@Component
public class FournitureEditController implements IController {
	@FXML
	public JFXTextField descriptionField;
	@FXML
	public JFXTextField nomField;
	@FXML
	public JFXTextField lieuDachatField;
	@FXML
	public JFXComboBox<String> uniteField;
	@FXML
	public FloatSpinner quantiteField;
	@FXML
	public JFXComboBox<String> uniteSecField;
	@FXML
	public FloatSpinner quantiteSecField;
	@FXML
	public JFXComboBox<String> typeField;
	@FXML
	public JFXButton addTypeButton;
	@FXML
	public JFXTextField referenceField;
	@FXML
	public JFXButton generateReferenceButton;
	@FXML
	public Label quantiteDisponibleLabel;
	@FXML
	public ImageView imagePane;
	@FXML
	public JFXButton addPictureWebBtn;
	@FXML
	public JFXButton pictureExpendBtn;
	@FXML
	public JFXButton addPictureBtn;
	@FXML
	public JFXButton addPictureClipboardBtn;
	@FXML
	public Label imageNotSaved;
	@FXML
	public Label intitulePrimLbl;
	@FXML
	public Label intituleSecLbl;
	@FXML
	public JFXButton archiverBtn;

	private final RootController root;
	private StageInitializer initializer;

	private FournitureDto fourniture;
	private boolean okClicked = false;

	private final TypeFournitureService typeService;
	private final FournitureService fournitureService;
	private final FourniturePictureHelper pictureHelper;

	public FournitureEditController(FourniturePictureHelper pictureHelper, FournitureService fournitureService,
			TypeFournitureService typeService, RootController root) {
		this.fournitureService = fournitureService;
		this.typeService = typeService;
		this.pictureHelper = pictureHelper;
		this.root = root;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;

		if (data == null || data.getFourniture() == null) {
			throw new IllegalData();
		}
		fourniture = data.getFourniture();
		if (fourniture == null || fourniture.getTypeNameProperty() == null) {
			fourniture = new FournitureDto();
		}

		quantiteField.setText(FxUtils.safePropertyToString(fourniture.getQuantiteProperty()));
		quantiteSecField.setText(FxUtils.safePropertyToString(fourniture.getQuantiteSecondaireProperty()));
		referenceField.setText(FxUtils.safePropertyToString(fourniture.getReferenceProperty()));
		descriptionField.setText(FxUtils.safePropertyToString(fourniture.getDescriptionProperty()));
		lieuDachatField.setText(FxUtils.safePropertyToString(fourniture.getLieuAchatProperty()));
		nomField.setText(FxUtils.safePropertyToString(fourniture.getNomProperty()));


		typeField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			TypeFourniture type = typeService.findTypeFourniture(newValue);
			boolean typeIsNull = type == null;
			boolean dimSecondaireIsNull = typeIsNull || type.getDimensionSecondaire() == null;

			uniteField.setDisable(typeIsNull);
			quantiteField.setDisable(typeIsNull);
			quantiteField.setText("0");

			if (!typeIsNull) {
				fourniture.setType(typeService.findTypeFourniture(newValue));
				uniteField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionPrincipale())));
				uniteField.setValue(type.getDimensionPrincipale().getDefault().getLabel());
				intitulePrimLbl.setText(type.getIntitulePrincipale());

			} else {
				uniteField.setValue(Strings.EMPTY);
				intitulePrimLbl.setText("N/A");
			}

			uniteSecField.setDisable(dimSecondaireIsNull);
			quantiteSecField.setDisable(dimSecondaireIsNull);
			quantiteSecField.setText("0");

			if (!dimSecondaireIsNull) {
				uniteSecField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionSecondaire())));
				uniteSecField.setValue(type.getDimensionSecondaire().getDefault().getLabel());
				intituleSecLbl.setText(type.getIntituleSecondaire());
			} else {
				uniteSecField.setValue(Strings.EMPTY);
				intituleSecLbl.setText("N/A");
			}
		});


		typeField.setItems(FXCollections.observableArrayList(typeService.getAllTypeFournituresValues()));
		typeField.setValue(FxUtils.safePropertyToString(fourniture.getTypeNameProperty()));

		intitulePrimLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleDimensionProperty()));
		intituleSecLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleSecondaireProperty()));

		if (fourniture.getType() != null) {
			uniteSecField.setValue(FxUtils.safePropertyToString(fourniture.getUniteSecondaireProperty()));
			uniteField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionPrincipale())));
			uniteSecField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionSecondaire())));
			uniteField.setValue(FxUtils.safePropertyToString(fourniture.getUniteProperty()));
			intitulePrimLbl.setText(fourniture.getType().getIntitulePrincipale());
			intituleSecLbl.setText(fourniture.getType().getIntituleSecondaire());
		}

		pictureHelper.setPane(imagePane, fourniture);
		boolean tissuIsNew = fourniture.getId() == 0;
		addPictureWebBtn.setDisable(tissuIsNew);
		pictureExpendBtn.setDisable(tissuIsNew);
		addPictureBtn.setDisable(tissuIsNew);
		imageNotSaved.setVisible(tissuIsNew);
		addPictureClipboardBtn.setDisable(tissuIsNew);
	}

	@FXML
	private void initialize() {
		addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
		generateReferenceButton.setGraphic(magicIcon);
		generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {

			setTissuFromFields();
			okClicked = true;
			root.displayFournituresDetails(fourniture);
		}
	}

	private void setTissuFromFields() {
		fourniture.setReference(referenceField.getText());
		fourniture.setQuantite(Float.parseFloat(quantiteField.getText()));
		fourniture.setQuantiteSec(Float.parseFloat(quantiteSecField.getText()));
		fourniture.setIntituleDimension(intitulePrimLbl.getText());
		fourniture.setDescription(descriptionField.getText());
		fourniture.setType(typeService.findTypeFourniture(typeField.getValue()));
		fourniture.setUnite(Unite.getEnum(uniteField.getValue()));
		fourniture.setUniteSecondaire(Unite.getEnum(uniteSecField.getValue()));
		fourniture.setIntituleSecondaire(intituleSecLbl.getText());
		fourniture.setLieuAchat(lieuDachatField.getText());
		fourniture.setNom(nomField.getText());
		
		fourniture = fournitureService.saveOrUpdate(fourniture);
	}

	@FXML
	private void handleCancel() {
		if (fourniture.getId() != 0) {
			root.displayFournituresDetails(fourniture);
		} else {
			root.displayFourniture();
		}
	}

	@FXML
	private void handleAddType() {
		initializer.displayModale(PathEnum.TYPE_FOURNITURE, null, "Fourniture");

		typeField.setItems(FXCollections.observableArrayList(typeService.getAllTypeFournituresValues()));
		typeField.setValue(FxUtils.safePropertyToString(fourniture.getTypeNameProperty()));

	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (StringUtils.isEmpty(referenceField.getText())) {
			errorMessage += "Référence non renseignée.\n";
		}
		if (typeField.getValue() == null) {
			errorMessage += "Type non renseignée.\n";
		}
		if (uniteField.getValue() == null) {
			errorMessage += "Unité non renseignée.\n";
		}

		if (StringUtils.isEmpty(errorMessage)) {
			return true;
		} else {
			// Show the error message.
			ShowAlert.erreur(initializer.getPrimaryStage(), "Valeurs incorrectes", "Merci de renseigner les champs suivants:", errorMessage);
			return false;
		}
	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		sb.append(typeField == null || Strings.isBlank(typeField.getValue()) ?
				"XXX" : typeField.getValue().length() > 3 ?
						typeField.getValue().substring(0, 3) : typeField.getValue());

		boolean ref = true;
		int refNb = 0;
		while (ref) {
			refNb++;
			ref = fournitureService.existByReference(sb.toString() + refNb);
		}
		referenceField.setText(sb.append(refNb).toString());
	}

	@FXML
	private void addPicture() {
		if (isInputValid()) {
			setTissuFromFields();
			pictureHelper.addPictureLocal(fourniture);
		}
	}

	@FXML
	private void addPictureWeb() {
		if (isInputValid()) {
			setTissuFromFields();
			pictureHelper.addPictureWeb(fourniture);
		}
	}

	@FXML
	private void addPictureFromClipboard(){
		if (isInputValid()) {
			setTissuFromFields();
			pictureHelper.addPictureClipBoard(fourniture);
		}
	}

	@FXML
	private void pictureExpend() {
		DevInProgressService.notImplemented();
	}

	@FXML
	public void archiver(){
		fourniture.setArchived(!fourniture.isArchived());
		fourniture = fournitureService.saveOrUpdate(fourniture);
		setBoutonArchiver();
	}

	private void setBoutonArchiver() {
		archiverBtn.setText(fourniture.isArchived() ? "Désarchiver" :"Archiver");
	}

}
