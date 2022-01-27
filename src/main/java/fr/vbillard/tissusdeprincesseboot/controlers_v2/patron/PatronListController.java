package fr.vbillard.tissusdeprincesseboot.controlers_v2.patron;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.services.PatronService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
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
        List<PatronDto> lst = patronService.getObservablePage(page, PAGE_SIZE);
        for (PatronDto p : lst){
        	FxData fxData = new FxData();
        	fxData.setPatron(p);
            Pane card = initializer.displayPane(PathEnum.PATRON_CARD, fxData);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    @FXML
    public void AddNewElement(MouseEvent mouseEvent) {
        rootController.displayPatronEdit(new PatronDto());
    }
}
