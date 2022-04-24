package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
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

	private ConstantesMetier constanteMetier;

	private Constants constants;

	private ModelMapper mapper;

	private TissuDto tissu;

	private CustomIcon customIcon;

	public TissuCardController(Constants constants, ConstantesMetier constanteMetier, CustomIcon customIcon,
			ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
		this.customIcon = customIcon;
		this.constanteMetier = constanteMetier;
		this.constants = constants;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null && data.getTissu() == null) {
			throw new IllegalData();
		}
		tissu = data.getTissu();
		setCardContent();

	}

	private void setCardContent() {
		boolean isDescription = tissu.getDescription() != null && !tissu.getDescription().equals(Strings.EMPTY);
		description.setText(isDescription ? tissu.getDescription() : constants.getAucuneDescription());
		laizeXlongueur.setText(FxUtils.safePropertyToString(tissu.getLongueurProperty()) + " cm x "
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

		masse.setStyleClass(tissu.getPoids() > constanteMetier.getMaxPoidsMoyen() ? "heavy-weight"
				: tissu.getPoids() > constanteMetier.getMinPoidsMoyen() ? "standard-weight" : "light-weight");
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
