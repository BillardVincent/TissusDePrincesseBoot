package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@Component
public class PatronListController extends ViewListController{
	
	private PatronService patronService;
    private RootController rootController;

    public PatronListController(PatronService patronService, RootController rootController){
        page = 0;
        this.patronService = patronService;
        this.rootController = rootController;
    }

    @Override
    protected void setElements() {
        cardPane.getChildren().clear();
        
        List<PatronDto> lst;
        
        if (specification instanceof PatronSpecification) {
			lst = patronService.getObservablePage(page, PAGE_SIZE, (PatronSpecification) specification);
		} else {
			lst = patronService.getObservablePage(page, PAGE_SIZE);

		}
        for (PatronDto p : lst){
        	FxData fxData = new FxData();
        	fxData.setPatron(p);
            Pane card = initializer.displayPane(PathEnum.PATRON_CARD, fxData);
            cardPane.getChildren().add(card);
        }
        setPageInfo(patronService.count());
    }

    @Override
    @FXML
    public void addNewElement(MouseEvent mouseEvent) {
        rootController.displayPatronEdit(new PatronDto());
    }
}
