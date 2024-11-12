package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
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

	private final ImageService imageService;

	private final RootController rootController;

	private final FournitureService fournitureService;

	private FournitureDto fourniture;
	
	private final Constants constants;

	public FournitureCardController(Constants constants, ImageService imageService, RootController rootController,
			FournitureService fournitureService) {
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
		nombreEtUnite.setText(safePropertyToString(fourniture.getQuantiteDisponibleProperty()) +
				safePropertyToString(fourniture.getUniteShortProperty()));
		type.setText(safePropertyToString(fourniture.getTypeNameProperty()));
		nom.setText(safePropertyToString(fourniture.getNomProperty()));
		Optional<Photo> pictures = imageService.getImage(fournitureService.convert(fourniture));
		image.setImage(imageService.imageOrDefault(pictures.orElse(null)));

	}

	public void setPrefHeight(Double height) {
		//TODO ?
	}

	@FXML
	public void showDetail() {
		rootController.displayFournituresDetails(fourniture);
	}
}
