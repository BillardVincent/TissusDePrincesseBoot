package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.services.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class ProjetEditController implements IController {

	@FXML
	private VBox scrollContent;

	@FXML
	private Label description;
	@FXML
	private Label marque;
	@FXML
	private Label modele;
	@FXML
	private ChoiceBox<String> status;

	private StageInitializer initializer;

	private ProjetService projetService;
	private TissuRequisService tissuRequisService;

	private ProjetDto projet;

	public ProjetEditController(ProjetService projetService, TissuRequisService tissuRequisService) {
		this.projetService = projetService;
		this.tissuRequisService = tissuRequisService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		this.initializer = initializer;
		if (data.length == 1 && data[0] instanceof ProjetDto) {
			projet = (ProjetDto) data[0];
		}
		setPane();
	}

	private void setPane() {
		 status.setItems(FXCollections.observableArrayList(ProjectStatus.labels()));
         status.setValue(projet.getProjectStatusProperty() == null ? ProjectStatus.BROUILLON.label : projet.getProjectStatus());

		marque.setText(projet.getPatron().getMarque());
		modele.setText(projet.getPatron().getModele());
		description.setText(projet.getDescription());
		List<TissuRequisDto> lst = tissuRequisService.getAllTissuRequisDtoByPatron(projet.getPatron().getId());
		for (TissuRequisDto tr : lst) {
			Pane element = initializer.displayPane(PathEnum.PROJET_EDIT_LIST_ELEMENT, tr);
			scrollContent.getChildren().add(element);
		}
	}

	@FXML
	private void handleOk() {
		
		projet.setDescription(description.getText());
		projet.setProjectStatus(status.getValue());
		projet = projetService.saveOrUpdate(projet);
		
	}

	@FXML
	private void handleCancel() {
	}

	
}
