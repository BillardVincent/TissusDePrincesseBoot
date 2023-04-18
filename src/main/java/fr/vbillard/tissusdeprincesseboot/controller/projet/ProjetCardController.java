package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

@Component
@Scope("prototype")
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

	private List<FontAwesomeIconView> listIcn;

	public ProjetCardController(ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getProjet() == null) {
			throw new IllegalData();
		}
		projet = data.getProjet();
		listIcn = Arrays.asList(iconeIdee, iconePlan, iconeEnCours, iconeFini);

		setCardContent();

	}

	private void setCardContent() {
		Optional<Photo> pictureTissu = imageService.getImage(mapper.map(projet, Projet.class));
		imageTissu.setImage(imageService.imageOrDefault(pictureTissu));
		Optional<Photo> picturePatron = imageService.getImage(mapper.map(projet.getPatron(), Patron.class));
		imagePatron.setImage(imageService.imageOrDefault(picturePatron));

		for (FontAwesomeIconView icon : listIcn) {
			icon.setFill(Color.GRAY);

			switch (ProjectStatus.getEnum(projet.getProjectStatus())) {
			case BROUILLON:
				iconeIdee.setFill(Constants.colorSecondary);
				break;
			case EN_COURS:
				iconeEnCours.setFill(Constants.colorSecondary);
				break;
			case PLANIFIE:
				iconePlan.setFill(Constants.colorSecondary);
				break;
			case TERMINE:
				iconeFini.setFill(Constants.colorSecondary);
				break;
				default:
					break;
			}
		}
	}

	@FXML
	public void showDetail() {
		rootController.displayProjetEdit(projet);
	}
}
