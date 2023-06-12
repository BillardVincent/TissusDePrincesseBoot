package fr.vbillard.tissusdeprincesseboot.controller.patron;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

@Component
public class PatronDetailController implements IController {

	@FXML
	public Label titre;
	@FXML
	private ImageView image;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private Label descriptionPatronLabel;
	@FXML
	private Label typeSupportPatronLabel;
	@FXML
	private TilePane listFournitures;

	private final RootController rootController;
	private StageInitializer initializer;
	private PatronDto patron;

	private final ProjetService projetService;
	private final PatronPictureHelper pictureUtils;

	PatronDetailController(PatronPictureHelper pictureUtils, RootController rootController,
			ProjetService projetService) {
		this.rootController = rootController;
		this.projetService = projetService;
		this.pictureUtils = pictureUtils;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getPatron() == null) {
			throw new IllegalData();
		}
		patron = data.getPatron();

		marquePatronLabel.setText(patron.getMarque());
		modelPatronLabel.setText(patron.getModele());
		typeVetementPatronLabel.setText(patron.getTypeVetement());
		typeSupportPatronLabel.setText(patron.getTypeSupport());

		// TODO patron version


		/*
		for (TissuRequisDto t : patron.getTissusRequis()) {
			FxData fxData = new FxData();
			fxData.setTissuRequis(t);
			Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, fxData);
			listFournitures.getChildren().add(element);
		}
		
		for (FournitureRequiseDto t : patron.getFournituresRequises()) {
			FxData fxData = new FxData();
			fxData.setFournitureRequise(t);
			Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, fxData);
			listFournitures.getChildren().add(element);
		}

		 */
		
		pictureUtils.setPane(image, patron);

	}

	public void edit() {
		rootController.displayPatronEdit(patron);

	}

	public void createProject() {
		ProjetDto projet = new ProjetDto();
		// TODO patron version

		//projet.setPatronVersion(patron);
		projet.setProjectStatus(ProjectStatus.BROUILLON);
		projet = projetService.saveOrUpdate(projet);
		rootController.displayProjetEdit(projet);
	}
	// TODO patron version

	/*
	 * txtInput.setEditable(false); txtInput.setMouseTransparent(true);
	 * txtInput.setFocusTraversable(false);
	 */
}
