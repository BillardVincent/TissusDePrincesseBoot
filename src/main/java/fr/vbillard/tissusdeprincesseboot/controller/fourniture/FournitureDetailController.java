package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;

@Component
public class FournitureDetailController implements IController {
	@FXML
	public Label nomField;
	@FXML
	public Label descriptionField;
	@FXML
	public Label lieuDachatField;
	@FXML
	public Label unitePrimField;
	@FXML
	public Label quantitePrimField;
	@FXML
	public Label intitulePrimLbl;
	@FXML
	public Label uniteSecField;
	@FXML
	public Label quantiteSecField;
	@FXML
	public Label intituleSecLbl;
	@FXML
	public Label typeField;
	@FXML
	public Label referenceField;
	@FXML
	public Label quantiteDisponibleLabel;
	@FXML
	public Label ancienneValeurInfo;
	@FXML
	public Label consommeLabel;
	@FXML
	public Label consommeInfo;
	@FXML
	public ImageView imagePane;

	public RowConstraints ancienneValeurRow;
	public RowConstraints consommeRow;

	@FXML
	private JFXButton addToButton;
	@FXML
	private JFXButton editButton;
	@FXML
	private JFXButton supprimerButton;

	private Optional<Photo> pictures;

	private StageInitializer initializer;

	private FournitureDto fourniture;
	private boolean okClicked = false;

	private FournitureService fournitureService;
	private RootController rootController;
	private ImageService imageService;
	private FournitureUsedService fournitureUsedService;

	public FournitureDetailController(ImageService imageService, RootController rootController,
			FournitureService fournitureService, FournitureUsedService tissuUsedService) {
		this.fournitureService = fournitureService;
		this.rootController = rootController;
		this.imageService = imageService;
		this.fournitureUsedService = tissuUsedService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getFourniture() == null) {
			throw new IllegalData();
		}
		fourniture = data.getFourniture();
		if (fourniture == null) {
			fourniture = new FournitureDto();
		}

		ancienneValeurInfo.setText(fourniture.getQuantiteProperty() == null ? "0" : Float.toString(fourniture.getQuantite()));
		consommeInfo.setText(Float.toString(fournitureService.getQuantiteUtilisee(fourniture.getId())));

		quantitePrimField.setText(FxUtils.safePropertyToString(fourniture.getQuantiteProperty()));
		quantiteSecField.setText(FxUtils.safePropertyToString(fourniture.getQuantiteSecondaireProperty()));
		intitulePrimLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleDimensionProperty()));
		intituleSecLbl.setText(FxUtils.safePropertyToString(fourniture.getIntituleSecondaireProperty()));
		referenceField.setText(FxUtils.safePropertyToString(fourniture.getReferenceProperty()));
		descriptionField.setText(FxUtils.safePropertyToString(fourniture.getDescriptionProperty()));
		lieuDachatField.setText(FxUtils.safePropertyToString(fourniture.getLieuAchatProperty()));
		nomField.setText(FxUtils.safePropertyToString(fourniture.getNomProperty()));
		typeField.setText(FxUtils.safePropertyToString(fourniture.getTypeNameProperty()));

		unitePrimField.setText(
				fourniture.getUnite() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUnite());
		uniteSecField.setText(
				fourniture.getUniteSecondaire() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUniteSecondaire());
		pictures = imageService.getImage(fournitureService.convert(fourniture));
		imagePane.setImage(imageService.imageOrDefault(pictures));

		addToButton.setVisible(rootController.hasFournitureRequiseSelected());
		editButton.setVisible(!rootController.hasFournitureRequiseSelected());

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void edit() {
		rootController.displayFournitureEdit(fourniture);

	}

	public void addTo() {
		rootController.addToSelected(fourniture);

	}

	public void delete() {
		if (fournitureUsedService.existsByFournitureId(fourniture.getId())) {
			ShowAlert.suppressionImpossible(initializer.getPrimaryStage(), EntityToString.FOURNITURE, fourniture.toString());
		} else {
		Optional<ButtonType> result = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.FOURNITURE,
				fourniture.toString());
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {

				pictures.ifPresent(photo -> imageService.delete(photo));
				fournitureService.delete(fourniture);
			}
			rootController.displayFourniture();
		}
	}
}
