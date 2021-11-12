package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

@Component
public class TissusController implements IController {
    private StageInitializer initializer;
    private TissuService tissuService;
    private int page;
    private final static int PAGE_SIZE = 15;

    @FXML
    private Pane cardPane;

    public TissusController(TissuService tissuService){
        page = 0;
        this.tissuService = tissuService;
        setTissus();
    }

    private void setTissus() {
        initializer.getData().setTissuList(tissuService.getObservablePage(page, PAGE_SIZE));
        for (TissuDto t : initializer.getData().getTissuList()){
            Pane card = initializer.displayPane(PathEnum.TISSUS_CARD);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    public void setStageInitializer(StageInitializer initializer) {
        this.initializer = initializer;
    }
}
