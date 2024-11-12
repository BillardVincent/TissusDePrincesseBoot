package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TissuCardController implements IController {

	private static final Logger LOGGER = LogManager.getLogger(TissuCardController.class);

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
	private Label poids;
	@FXML
	private Label unitePoids;
	@FXML
	private MaterialDesignIconView masse;
	@FXML
	private HBox footer;
	@FXML
	private ImageView decatiIcn;
	@FXML
	private ImageView image;

	private final ImageService imageService;

	private final RootController rootController;

	private final Constants constants;

	private final ModelMapper mapper;

	private TissuDto tissu;

	private final CustomIcon customIcon;

	private final UserPrefService userPrefService;

	public TissuCardController(Constants constants, UserPrefService userPrefService, CustomIcon customIcon,
			ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
		this.customIcon = customIcon;
		this.userPrefService = userPrefService;
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
		typeView.getChildren().add(customIcon.typeTissu(TypeTissuEnum.getEnum(tissu.getTypeTissu()), 40));
		tissage.setText(FxUtils.safePropertyToString(tissu.getTissageProperty()));
		poids.setText(FxUtils.safePropertyToString(tissu.getPoidseProperty()));
		unitePoids.setText(tissu.getUnitePoids());
		ImageView decatiView;
		if (tissu.isDecati()) {
			decatiView = customIcon.washingMachinIcon(20);
		} else {
			decatiView = customIcon.noWashingMachinIcon(20);
		}
		footer.getChildren().add(decatiView);


		masse.setStyleClass(styleWeight());
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures.orElse(null)));

	}

	private String styleWeight(){
		UserPref pref = userPrefService.getUser();

		if (tissu.getPoids() > pref.getMaxPoidsMoyen()) {
			return "heavy-weight";
		}
		if (tissu.getPoids() > pref.getMinPoidsMoyen()) {
			return "standard-weight";
		}
		return "light-weight";
	}

	//TODO
	public void setPrefHeight(Double height) {
		LOGGER.error(height);
	}

	@FXML
	public void showDetail() {
		rootController.displayTissusDetails(tissu);
	}
}
