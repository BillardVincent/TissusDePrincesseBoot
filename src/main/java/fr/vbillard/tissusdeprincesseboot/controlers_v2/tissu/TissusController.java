package fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu;

import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.ViewListController;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
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
        initializer.getData().setTissuList(tissuService.getObservablePage(page, PAGE_SIZE));
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
