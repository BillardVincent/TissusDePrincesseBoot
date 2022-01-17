package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private Label statut;

	private StageInitializer initializer;

	private ProjetService projetService;

	private ProjetDto projet;

	public ProjetEditController(ProjetService projetService) {
		this.projetService = projetService;
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
		for (TissuRequisDto tr : projet.getPatron().getTissusRequis()) {
			Pane element = initializer.displayPane(PathEnum.PROJET_EDIT_LIST_ELEMENT, tr);
			scrollContent.getChildren().add(element);
		}
	}

	@FXML
	private void handleOk() {
		
		projet.setDescription(description.getText());
		projet.setProjectStatus(statut.getText());
		projet = projetService.saveOrUpdate(projet);
		
	}

	@FXML
	private void handleCancel() {
	}

	
}
