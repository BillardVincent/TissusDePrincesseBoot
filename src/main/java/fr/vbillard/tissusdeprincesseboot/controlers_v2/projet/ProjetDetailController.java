package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class ProjetDetailController implements IController {

	@FXML
	private Label descriptionProjetPanLabel;
	@FXML
	private Label marqueProjetPanLabel;
	@FXML
	private Label modelProjePantLabel;
	@FXML
	private Label typeVetementPanProjetLabel;
	@FXML
	private Label projetStatusPanLabel;

	private ProjetDto projet;
	
	private StageInitializer initializer;
	private ModelMapper mapper;
	private ProjetService projetService;
	private RootController rootController;

	public ProjetDetailController(ModelMapper mapper, ProjetService projetService, RootController rootController) {
		this.mapper = mapper;
		this.projetService = projetService;
		this.rootController = rootController;
	}

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
				projetStatusPanLabel.setText(projet.getProjectStatus());

			} else {
				descriptionProjetPanLabel.setText("");
				marqueProjetPanLabel.setText("");
				modelProjePantLabel.setText("");
				typeVetementPanProjetLabel.setText("");
				projetStatusPanLabel.setText("");
			}
		

		// setButtons();
	}
	
    public void edit(){
        rootController.displayProjetEdit(projet);

    }
}
