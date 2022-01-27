package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
public class ProjetCardController implements IController{
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

    private ProjetDto projet;

    public ProjetCardController(ImageService imageService, RootController rootController, ModelMapper mapper) {
        this.imageService = imageService;
        this.rootController = rootController;
        this.mapper = mapper;
    }

    @Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null && data.getProjet() == null) {
			throw new IllegalData();
		}
		projet = data.getProjet();
            setCardContent();
        
    }

    private void setCardContent() {
    	
    	
        Photo pictures = imageService.getImage(mapper.map(projet, Projet.class));
        image.setImage(imageService.imageOrDefault(pictures));
        
    }


    @FXML
    public void showDetail() {
        rootController.displayProjetDetails(projet);
    }
}
