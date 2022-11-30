package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.FourniturePictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
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
	public JFXTextField quantiteField;
	@FXML
	public JFXComboBox<String> uniteSecField;
	@FXML
	public JFXTextField quantiteSecField;
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
	public Label imageNotSaved;
	@FXML
	public Label intitulePrimLbl;
	@FXML
	public Label intituleSecLbl;

	private RootController root;
	private StageInitializer initializer;

	private FournitureDto fourniture;
	private boolean okClicked = false;

	private TypeFournitureService typeService;
	private FournitureService fournitureService;
	private FourniturePictureHelper pictureHelper;

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

		uniteField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionPrincipale())));
		uniteField.setValue(
				fourniture.getUniteProperty() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUnite());
		uniteSecField.setItems(FXCollections.observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionSecondaire())));
		uniteSecField.setValue(
				fourniture.getUniteSecondaireProperty() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUniteSecondaire());

		typeField.setItems(FXCollections.observableArrayList(typeService.getAllTypeFournituresValues()));
		typeField.setValue(FxUtils.safePropertyToString(fourniture.getTypeNameProperty()));

		intitulePrimLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleDimensionProperty()));
		intituleSecLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleSecondaireProperty()));
		
		pictureHelper.setPane(imagePane, fourniture);
		boolean tissuIsNew = fourniture.getId() == 0;
		addPictureWebBtn.setDisable(tissuIsNew);
		pictureExpendBtn.setDisable(tissuIsNew);
		addPictureBtn.setDisable(tissuIsNew);
		imageNotSaved.setVisible(tissuIsNew);
	}

	@FXML
	private void initialize() {
		addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());
		FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
		generateReferenceButton.setGraphic(magicIcon);
		generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

		CustomSpinner.setLongSpinner(quantiteField);
		
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
		fourniture.setQuantiteSecondaire(Float.parseFloat(quantiteSecField.getText()));
		fourniture.setDescription(descriptionField.getText());
		fourniture.setType(typeService.findTypeFourniture(typeField.getValue()));
		fourniture.setUnite(Unite.getEnum(uniteField.getValue()));
		fourniture.setUniteSecondaire(Unite.getEnum(uniteSecField.getValue()));
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

		if (referenceField.getText() == null || referenceField.getText().length() == 0) {
			errorMessage += "Référence non renseignée.\n";
		}
		if (typeField.getValue() == null) {
			errorMessage += "Type non renseignée.\n";
		}
		if (uniteField.getValue() == null) {
			errorMessage += "Unité non renseignée.\n";
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
	private void pictureExpend() {
		DevInProgressService.notImplemented();
	}

}
