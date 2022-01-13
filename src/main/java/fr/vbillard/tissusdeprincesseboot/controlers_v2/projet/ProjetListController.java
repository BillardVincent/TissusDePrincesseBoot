package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;

@Component
public class ProjetListController extends ViewListController{
	
    private ProjetService projetService;
    private RootController rootController;
    
    public ProjetListController(ProjetService projetService, RootController rootController) {
    	this.projetService = projetService;
    	this.rootController = rootController;
        page = 0;
    }
    
    @Override
    protected void setElements() {
        cardPane.getChildren().clear();
        initializer.getData().setProjetList(projetService.getObservablePage(page, PAGE_SIZE));
        for (TissuDto t : initializer.getData().getTissuList()){
            Pane card = initializer.displayPane(PathEnum.TISSUS_CARD, t);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    @FXML
    public void AddNewElement(MouseEvent mouseEvent) {
        rootController.displayTissusEdit(new TissuDto());
    }

}
