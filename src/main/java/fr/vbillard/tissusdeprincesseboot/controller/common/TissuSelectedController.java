package fr.vbillard.tissusdeprincesseboot.controller.common;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TissuSelectedController implements IController {

	@FXML
	public Label description;
	@FXML
	public Label titre;
	@FXML
	private Label laizeXlongueur;
	@FXML
	private Label matiere;
	@FXML
	private Pane typeView;
	@FXML
	private Label tissage;
	@FXML
	private ImageView image;

	private final ImageService imageService;

	private final RootController rootController;

	private final Constants constants;

	private final ModelMapper mapper;

	private TissuDto tissu;

	private final CustomIcon customIcon;


	public TissuSelectedController(Constants constants, CustomIcon customIcon,
                                   ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
		this.customIcon = customIcon;
		this.constants = constants;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getTissu() == null) {
			throw new IllegalData();
		}
		tissu = data.getTissu();
		setCardContent();

	}

	private void setCardContent() {
		boolean isDescription = tissu.getDescription() != null && !tissu.getDescription().equals(Strings.EMPTY);
		description.setText(isDescription ? tissu.getDescription() : constants.getAucuneDescription());
		laizeXlongueur.setText(FxUtils.safePropertyToString(tissu.getLongueurRestanteProperty()) + " cm x "
				+ FxUtils.safePropertyToString(tissu.getLaizeProperty()) + " cm");
		matiere.setText(FxUtils.safePropertyToString(tissu.getMatiereProperty()));
		typeView.getChildren().add(customIcon.typeTissu(TypeTissuEnum.getEnum(tissu.getTypeTissu()), 20));
		typeView.setPrefSize(20, 20);
		tissage.setText(FxUtils.safePropertyToString(tissu.getTissageProperty()));

		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		rootController.displayTissusDetails(tissu);
	}
}
