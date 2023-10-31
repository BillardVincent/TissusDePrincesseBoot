package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.DECIMAL_FORMAT;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;

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
	@FXML
	public Rectangle color;

	public RowConstraints ancienneValeurRow;
	public RowConstraints consommeRow;
    public Label lieuDeStockageLbl;

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

	private final FournitureService fournitureService;
	private final RootController rootController;
	private final ImageService imageService;
	private final RangementService rangementService;
	private FournitureUsedService fournitureUsedService;

	public FournitureDetailController(ImageService imageService, RootController rootController,
			FournitureService fournitureService, RangementService rangementService, FournitureUsedService tissuUsedService) {
		this.fournitureService = fournitureService;
		this.rootController = rootController;
		this.imageService = imageService;
		this.rangementService = rangementService;
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

		ancienneValeurInfo.setText(fourniture.getQuantiteProperty() == null ? "0" : DECIMAL_FORMAT.format(fourniture.getQuantite()));
		consommeInfo.setText(DECIMAL_FORMAT.format(fournitureService.getQuantiteUtilisee(fourniture.getId())));

		quantitePrimField.setText(safePropertyToString(fourniture.getQuantiteProperty()));
		quantiteSecField.setText(safePropertyToString(fourniture.getQuantiteSecondaireProperty()));
		intitulePrimLbl.setText(safePropertyToString(fourniture.getIntituleDimensionProperty()));
		intituleSecLbl.setText(safePropertyToString(fourniture.getIntituleSecondaireProperty()));
		referenceField.setText(safePropertyToString(fourniture.getReferenceProperty()));
		descriptionField.setText(safePropertyToString(fourniture.getDescriptionProperty()));
		lieuDachatField.setText(safePropertyToString(fourniture.getLieuAchatProperty()));
		nomField.setText(safePropertyToString(fourniture.getNomProperty()));
		typeField.setText(safePropertyToString(fourniture.getTypeNameProperty()));

		unitePrimField.setText(
				fourniture.getUnite() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUnite());
		uniteSecField.setText(
				fourniture.getUniteSecondaire() == null ? Unite.NON_RENSEIGNE.getLabel() : fourniture.getUniteSecondaire());
		pictures = imageService.getImage(fournitureService.convert(fourniture));
		imagePane.setImage(imageService.imageOrDefault(pictures));

		if (fourniture.getRangement() == null){
			lieuDeStockageLbl.setText("Non renseigné");
		} else {
			lieuDeStockageLbl.setText(rangementService.getRangementPath(fourniture.getRangement().getId()));
		}

		color.setFill(fourniture.getColor() != null ? fourniture.getColor() : Color.TRANSPARENT);

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
		if (result.orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {

				pictures.ifPresent(imageService::delete);
				fournitureService.delete(fourniture);
			}
			rootController.displayFourniture();
		}
	}
}
