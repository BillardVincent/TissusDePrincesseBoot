package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.workflow.Workflow;
import fr.vbillard.tissusdeprincesseboot.service.workflow.WorkflowService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
public class ProjetEditController implements IController {

	@FXML
	private ScrollPane scrollContent;
	@FXML
	private Label description;
	@FXML
	private Label marque;
	@FXML
	private Label modele;
	@FXML
	private ImageView patronPicture;
	@FXML
	private FontAwesomeIconView iconeIdee;
	@FXML
	private FontAwesomeIconView iconePlan;
	@FXML
	private FontAwesomeIconView iconeEnCours;
	@FXML
	private FontAwesomeIconView iconeFini;
	@FXML
	private JFXButton nextStep;
	@FXML
	private JFXButton previousStep;

	private List<FontAwesomeIconView> listIcn;

	private StageInitializer initializer;

	private final WorkflowService workflowService;

	private final RootController root;
	private final ProjetService projetService;
	private final TissuRequisService tissuRequisService;
	private final FournitureRequiseService fournitureRequiseService;
	private final ImageService imageService;
	private ModelMapper mapper;
	private Workflow workflow;

	private ProjetDto projet;

	public ProjetEditController(RootController root, ProjetService projetService,
			FournitureRequiseService fournitureRequiseService, TissuRequisService tissuRequisService,
			ImageService imageService, ModelMapper mapper, WorkflowService workflowService) {
		this.projetService = projetService;
		this.tissuRequisService = tissuRequisService;
		this.root = root;
		this.imageService = imageService;
		this.mapper = mapper;
		this.workflowService = workflowService;
		this.fournitureRequiseService = fournitureRequiseService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getProjet() == null) {
			throw new IllegalData();
		}
		projet = data.getProjet();

		listIcn = Arrays.asList(iconeIdee, iconePlan, iconeEnCours, iconeFini);
		setPane();
	}

	private void setPane() {
		VBox content = new VBox();
		content.setSpacing(10);

		marque.setText(projet.getPatron().getMarque());
		modele.setText(projet.getPatron().getModele());
		description.setText(projet.getDescription());
		List<TissuRequisDto> lst = tissuRequisService.getAllTissuRequisDtoByPatron(projet.getPatron().getId());
		for (TissuRequisDto tr : lst) {
			FxData data = new FxData();
			data.setTissuRequis(tr);
			data.setProjet(projet);
			Pane element = initializer.displayPane(PathEnum.PROJET_EDIT_TISSU_LIST_ELEMENT, data);
			content.getChildren().add(element);
		}
		List<FournitureRequiseDto> lstFourniture =
				fournitureRequiseService.getAllFournitureRequiseDtoByPatron(projet.getPatron().getId());
		for (FournitureRequiseDto fr : lstFourniture) {
			FxData data = new FxData();
			data.setFournitureRequise(fr);
			data.setProjet(projet);
			Pane element = initializer.displayPane(PathEnum.PROJET_EDIT_FOURNITURE_LIST_ELEMENT, data);
			content.getChildren().add(element);
		}

		scrollContent.setContent(content);

		Optional<Photo> picturePatron = imageService.getImage(mapper.map(projet.getPatron(), Patron.class));
		patronPicture.setImage(imageService.imageOrDefault(picturePatron));

		ProjectStatus status = ProjectStatus.getEnum(projet.getProjectStatus());

		for (FontAwesomeIconView icon : listIcn) {
			icon.setFill(Color.LIGHTGRAY);

			switch (Objects.requireNonNull(status)) {
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
			}
		}

		workflow = workflowService.getWorkflow(mapper.map(projet, Projet.class));

		nextStep.setDisable(!workflow.isNextPossible());
		previousStep.setDisable(!workflow.isCancelPossible());
	}

	@FXML
	private void handleOk() {

		projet.setDescription(description.getText());
		projet = projetService.saveOrUpdate(projet);

	}

	@FXML
	private void handleCancel() {

		root.displayPatrons();

	}

	@FXML
	private void nextStep() {
		workflow.nextStep(mapper.map(projet, Projet.class));
		projet = mapper.map(projetService.getById(projet.getId()), ProjetDto.class);
		root.displayProjetEdit(projet);
	}

	@FXML
	private void previousStep() {
		workflow.cancel(mapper.map(projet, Projet.class));
		projet = mapper.map(projetService.getById(projet.getId()), ProjetDto.class);
		root.displayProjetEdit(projet);

	}

	@FXML
	private void delete() {

	}

}
