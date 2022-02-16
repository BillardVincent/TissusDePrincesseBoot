package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@Component
public class TissusController extends ViewListController {

    private TissuService tissuService;
    private RootController rootController;

    public TissusController(TissuService tissuService, RootController rootController){
        page = 0;
        this.tissuService = tissuService;
        this.rootController = rootController;
    }

    @Override
    protected void setElements() {
        cardPane.getChildren().clear();
        List<TissuDto> lstTissu = tissuService.getObservablePage(page, PAGE_SIZE);
        for (TissuDto t : lstTissu){
        	FxData data = new FxData();
        	data.setTissu(t);
            Pane card = initializer.displayPane(PathEnum.TISSUS_CARD, data);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    @FXML
    public void AddNewElement(MouseEvent mouseEvent) {
        rootController.displayTissusEdit(new TissuDto());
    }

}
