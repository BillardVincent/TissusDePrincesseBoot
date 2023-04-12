package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

@Component
@Scope("prototype")
public class FournitureCardController implements IController {

	@FXML
	public Label description;
	@FXML
	public Label nom;
	@FXML
	private Label nombreEtUnite;
	@FXML
	private Label type;
	@FXML
	private Label zero_zero;
	@FXML
	private HBox footer;
	@FXML
	private ImageView image;

	private ImageService imageService;

	private RootController rootController;

	private FournitureService fournitureService;

	private FournitureDto fourniture;
	
	private Constants constants;

	public FournitureCardController(Constants constants, CustomIcon customIcon,
			ImageService imageService, RootController rootController, FournitureService fournitureService) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.fournitureService = fournitureService;
		this.constants = constants;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getFourniture() == null) {
			throw new IllegalData();
		}
		fourniture = data.getFourniture();
		setCardContent();

	}

	private void setCardContent() {
		boolean isDescription = fourniture.getDescription() != null && !fourniture.getDescription().equals(Strings.EMPTY);
		description.setText(isDescription ? fourniture.getDescription() : constants.getAucuneDescription());
		nombreEtUnite.setText(FxUtils.safePropertyToString(fourniture.getQuantiteDisponibleProperty()) + 
				FxUtils.safePropertyToString(fourniture.getUniteProperty()));
		type.setText(FxUtils.safePropertyToString(fourniture.getTypeNameProperty()));
		nom.setText(FxUtils.safePropertyToString(fourniture.getNomProperty()));
		Optional<Photo> pictures = imageService.getImage(fournitureService.convert(fourniture));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	public void setPrefHeight(Double height) {
		System.out.println(height);
	}

	@FXML
	public void showDetail() {
		rootController.displayFournituresDetails(fourniture);
	}
}
