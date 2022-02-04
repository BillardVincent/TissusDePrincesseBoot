package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class TissuUsedCardController implements IController {

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
	private FontAwesomeIconView decati;
	@FXML
	private ImageView image;

	private ImageService imageService;

	private RootController rootController;

	private ModelMapper mapper;

	private TissuUsed tissuUsed;
	
	private TissuDto tissu;

	public TissuUsedCardController(ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getTissuUsed() == null) {
			throw new IllegalData();
		}
		tissuUsed = data.getTissuUsed();
		tissu = mapper.map(tissuUsed.getTissu(), TissuDto.class);
		setCardContent();
	}

	private void setCardContent() {
		description.setText(tissu.getDescription());
		longueur.setText(String.valueOf(tissuUsed.getLongueur()));
		laize.setText(String.valueOf(tissu.getLaize()));
		matiere.setText(tissu.getMatiere());
		type.setText(tissu.getType());
		tissage.setText(tissu.getTissage());
		poids.setText(String.valueOf(tissu.getPoids()));
		unitePoids.setText(tissu.getUnitePoids());
		decati.setFill(tissu.isDecati() ? Constants.colorAdd : Constants.colorDelete);
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
