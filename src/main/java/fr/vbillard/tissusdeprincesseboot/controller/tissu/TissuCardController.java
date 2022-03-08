package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class TissuCardController implements IController {

	@FXML
	public Label description;
	@FXML
	public Label titre;
	@FXML
	private Label longueur;
	@FXML
	private Label laize;
	@FXML
	private Label matiere;
	@FXML
	private Label type;
	@FXML
	private Label tissage;
	@FXML
	private Label poids;
	@FXML
	private Label unitePoids;
	@FXML
	private MaterialDesignIconView masse;
	@FXML
	private ImageView decatiIcn;
	@FXML
	private ImageView image;

	private ImageService imageService;

	private RootController rootController;

	private ModelMapper mapper;

	private TissuDto tissu;
	
	private CustomIcon customIcon;

	public TissuCardController(CustomIcon customIcon, ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
		this.customIcon = customIcon;
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
		description.setText(tissu.getDescription());
		longueur.setText(FxUtils.safePropertyToString(tissu.getLongueurProperty()));
		laize.setText(FxUtils.safePropertyToString(tissu.getLaizeProperty()));
		matiere.setText(FxUtils.safePropertyToString(tissu.getMatiereProperty()));
		type.setText(FxUtils.safePropertyToString(tissu.getTypeProperty()));
		tissage.setText(FxUtils.safePropertyToString(tissu.getTissageProperty()));
		poids.setText(FxUtils.safePropertyToString(tissu.getPoidseProperty()));
		//TODO sécuriser si null
		unitePoids.setText(tissu.getUnitePoids());
		decatiIcn = customIcon.washingMachinIcon();
		decatiIcn.setVisible(tissu.isDecati());
		masse.setStyleClass(tissu.getPoids() > ConstantesMetier.MAX_POIDS_MOYEN ? "heavy-weight"
				: tissu.getPoids() > ConstantesMetier.MIN_POIDS_MOYEN ? "standard-weight" : "light-weight");
		Photo pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		rootController.displayTissusDetails(tissu);
	}
}
