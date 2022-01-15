package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class ProjetEditController implements IController{
	
	@FXML
	VBox scrollContent;
	
	StageInitializer initializer;
	
	ProjetService projetService;
	
	ProjetDto projet;
	
	public ProjetEditController(ProjetService projetService) {
		this.projetService = projetService;
	}
	
	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		this.initializer = initializer;
        if (data.length == 1 && data[0] instanceof ProjetDto){
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
		/*
		if (isInputValid()) {
			if (tissu.getChuteProperty() == null) {
				tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, typeTissuService.getAll().get(0), 0,
						UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
			}
			tissu.setReference(referenceField.getText());
			tissu.setLongueur(longueur);
			tissu.setLaize(laizeField.getValue());
			tissu.setDescription(descriptionField.getText());
			tissu.setMatiere(matiereField.getValue());
			tissu.setType(typeField.getValue());
			tissu.setPoids(poidsField.getValue());
			tissu.setUnitePoids(unitePoidsField.getValue());
			tissu.setDecati(Boolean.parseBoolean(decatiField.getText()));
			tissu.setLieuAchat(lieuDachatField.getText());
			tissu.setChute(Boolean.parseBoolean(chuteField.getText()));
			tissu.setTissage(tissageField.getValue());

			tissu = tissuService.saveOrUpdate(tissu);
			okClicked = true;
			*/
		
	}

	@FXML
	private void handleCancel() {
	}
}
