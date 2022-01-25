package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
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
	public void setStageInitializer(StageInitializer initializer, FxDto... data) {
		this.initializer = initializer;
		if (data.length == 2) {
			if (data[0] instanceof ProjetDto) {
				projet = (ProjetDto) data[0];
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

				if (data[0] instanceof TissuRequisDto) {
					tissuRequis = (TissuRequisDto) data[1];
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
				}
			}
		}
		setPane();
	}

	private void setPane() {
		// TODO Auto-generated method stub
		
	}
}
