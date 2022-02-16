package fr.vbillard.tissusdeprincesseboot.controller;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class SelectionController implements IController {

	@FXML
	private Label descriptionProjetPanLabel;
	@FXML
	private Label marqueProjetPanLabel;
	@FXML
	private Label modelProjePantLabel;
	@FXML
	private Label typeVetementPanProjetLabel;

	private StageInitializer initializer;
	private ProjetDto projet;
	private TissuRequisDto tissuRequis;

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null) {
			throw new IllegalData();
		}
		projet = data.getProjet();
		if (projet != null) {
			descriptionProjetPanLabel.setText(projet.getDescription());
			marqueProjetPanLabel.setText(projet.getPatron().getMarque());
			modelProjePantLabel.setText(projet.getPatron().getModele());
			typeVetementPanProjetLabel.setText(projet.getPatron().getTypeVetement());

		} else {
			descriptionProjetPanLabel.setText("");
			marqueProjetPanLabel.setText("");
			modelProjePantLabel.setText("");
			typeVetementPanProjetLabel.setText("");
		}

		tissuRequis = data.getTissuRequis();
		if (projet != null) {
			descriptionProjetPanLabel.setText(projet.getDescription());
			marqueProjetPanLabel.setText(projet.getPatron().getMarque());
			modelProjePantLabel.setText(projet.getPatron().getModele());
			typeVetementPanProjetLabel.setText(projet.getPatron().getTypeVetement());

		} else {
			descriptionProjetPanLabel.setText("");
			marqueProjetPanLabel.setText("");
			modelProjePantLabel.setText("");
			typeVetementPanProjetLabel.setText("");
		}

		setPane();
	}

	private void setPane() {
		// TODO Auto-generated method stub

	}
}
