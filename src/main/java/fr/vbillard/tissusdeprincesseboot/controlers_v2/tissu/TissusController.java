package fr.vbillard.tissusdeprincesseboot.controlers_v2.tissu;

import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

@Component
public class TissusController implements IController {

    private StageInitializer initializer;
    private TissuService tissuService;
    private RootController rootController;
    private int page;
    private final static int PAGE_SIZE = 10;

    @FXML
    private TilePane cardPane;
    @FXML
    public FontAwesomeIconView previousIcon;
    @FXML
    public FontAwesomeIconView nextIcon;

    public TissusController(TissuService tissuService, RootController rootController){
        page = 0;
        this.tissuService = tissuService;
        this.rootController = rootController;
    }

    private void setTissus() {
        cardPane.getChildren().clear();
        initializer.getData().setTissuList(tissuService.getObservablePage(page, PAGE_SIZE));
        for (TissuDto t : initializer.getData().getTissuList()){
            Pane card = initializer.displayPane(PathEnum.TISSUS_CARD, t);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        page = 0;
        this.initializer = initializer;
        setTissus();

    }

    public void AddNewTissu(MouseEvent mouseEvent) {
        rootController.displayTissusEdit(new TissuDto());
    }

    public void PreviousPage(MouseEvent mouseEvent) {
        if (page > 0) {
            page --;
        }
        setTissus();
    }

    public void NextPage(MouseEvent mouseEvent) {
        page ++;
    setTissus();
    }
}
