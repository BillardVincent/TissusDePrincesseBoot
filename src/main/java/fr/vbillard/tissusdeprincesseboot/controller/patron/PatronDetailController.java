package fr.vbillard.tissusdeprincesseboot.controller.patron;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
	private ScrollPane listFournitures;

	private RootController rootController;
	private StageInitializer initializer;
	private PatronDto patron;

	private ModelMapper mapper;
	private ProjetService projetService;

	PatronDetailController(ModelMapper mapper, RootController rootController, ProjetService projetService) {
		this.mapper = mapper;
		this.rootController = rootController;
		this.projetService = projetService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null && data.getPatron() == null) {
			throw new IllegalData();
		}
		patron = data.getPatron();

			marquePatronLabel.setText(patron.getMarque());
			modelPatronLabel.setText(patron.getModele());
			typeVetementPatronLabel.setText(patron.getTypeVetement());

			VBox root = new VBox();
			root.setSpacing(10);

			for (TissuRequisDto t : patron.getTissusRequis()) {
				FxData fxData = new FxData();
				fxData.setTissuRequis(t);
				Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, fxData);
				root.getChildren().add(element);
			}
			listFournitures.setContent(root);

		
	}

	public void edit() {
		rootController.displayPatronEdit(patron);

	}

	public void createProject() {
		ProjetDto projet = new ProjetDto();
		projet.setPatron(patron);
		projet.setProjectStatus(ProjectStatus.BROUILLON);
		projet = projetService.saveOrUpdate(projet);
		rootController.displayProjetEdit(projet);
	}

	/*
	 * txtInput.setEditable(false); txtInput.setMouseTransparent(true);
	 * txtInput.setFocusTraversable(false);
	 */
}
