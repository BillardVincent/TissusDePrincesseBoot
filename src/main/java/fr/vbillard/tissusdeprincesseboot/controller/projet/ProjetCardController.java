package fr.vbillard.tissusdeprincesseboot.controller.projet;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Optional;

@Component
public class ProjetCardController implements IController {
	@FXML
	public Label titre;

	@FXML
	private FontAwesomeIconView iconeIdee;
	@FXML
	private FontAwesomeIconView iconePlan;
	@FXML
	private FontAwesomeIconView iconeEnCours;
	@FXML
	private FontAwesomeIconView iconeFini;
	@FXML
	private ImageView imagePatron;
	@FXML
	private ImageView imageTissu;

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
		Optional<Photo> pictureTissu = imageService.getImage(mapper.map(projet, Projet.class));
		imageTissu.setImage(imageService.imageOrDefault(pictureTissu));
		Optional<Photo> picturePatron = imageService.getImage(mapper.map(projet.getPatron(), Patron.class));
		imagePatron.setImage(imageService.imageOrDefault(picturePatron));

	}

	@FXML
	public void showDetail() {
		rootController.displayProjetDetails(projet);
	}
}
