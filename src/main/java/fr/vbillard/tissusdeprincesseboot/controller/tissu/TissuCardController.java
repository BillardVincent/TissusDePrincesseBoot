package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

@Component
@Scope("prototype")
public class TissuCardController implements IController {

	@FXML
	public Label description;
	@FXML
	public Label titre;
	@FXML
	private Label laizeXlongueur;
	@FXML
	private Label matiere;
	@FXML
	private WebView typeView;
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

	private ImageService imageService;

	private RootController rootController;

	private Constants constants;

	private ModelMapper mapper;

	private TissuDto tissu;

	private CustomIcon customIcon;

	private UserPrefService userPrefService;

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
		customIcon.typeTissu(typeView, TypeTissuEnum.getEnum(tissu.getTypeTissu()));
		tissage.setText(FxUtils.safePropertyToString(tissu.getTissageProperty()));
		poids.setText(FxUtils.safePropertyToString(tissu.getPoidseProperty()));
		unitePoids.setText(tissu.getUnitePoids());
		WebView view = new WebView();
		if (tissu.isDecati()) {
			customIcon.washingMachinIcon(view, 20);
		} else {
			customIcon.noWashingMachinIcon(view, 20);
		}
		footer.getChildren().add(view);

		UserPref pref = userPrefService.getUser();

		masse.setStyleClass(tissu.getPoids() > pref.getMaxPoidsMoyen() ? "heavy-weight"
				: tissu.getPoids() > pref.getMinPoidsMoyen() ? "standard-weight" : "light-weight");
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	public void setPrefHeight(Double height) {
		System.out.println(height);
	}

	@FXML
	public void showDetail() {
		rootController.displayTissusDetails(tissu);
	}
}
