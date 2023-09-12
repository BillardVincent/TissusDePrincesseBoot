package fr.vbillard.tissusdeprincesseboot.controller.misc;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

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
			// TODO patron version

			/*
			marqueProjetPanLabel.setText(projet.getPatronVersion().getMarque());
			modelProjePantLabel.setText(projet.getPatronVersion().getModele());
			typeVetementPanProjetLabel.setText(projet.getPatronVersion().getTypeVetement());

			 */

		} else {
			descriptionProjetPanLabel.setText("");
			marqueProjetPanLabel.setText("");
			modelProjePantLabel.setText("");
			typeVetementPanProjetLabel.setText("");
		}

		tissuRequis = data.getTissuRequis();
		if (projet != null) {
			descriptionProjetPanLabel.setText(projet.getDescription());
			// TODO patron version
/*
			marqueProjetPanLabel.setText(projet.getPatronVersion().getMarque());
			modelProjePantLabel.setText(projet.getPatronVersion().getModele());
			typeVetementPanProjetLabel.setText(projet.getPatronVersion().getTypeVetement());
			*/

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
