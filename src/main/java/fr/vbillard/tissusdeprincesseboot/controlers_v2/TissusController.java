package fr.vbillard.tissusdeprincesseboot.controlers_v2;

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
    private int page;
    private final static int PAGE_SIZE = 15;

    @FXML
    private TilePane cardPane;
    @FXML
    public FontAwesomeIconView previousIcon;
    @FXML
    public FontAwesomeIconView nextIcon;

    public TissusController(TissuService tissuService){
        page = 0;
        this.tissuService = tissuService;
    }

    private void setTissus() {
        initializer.getData().setTissuList(tissuService.getObservablePage(page, PAGE_SIZE));
        System.out.println(initializer.getData().getTissuList().size());
        for (TissuDto t : initializer.getData().getTissuList()){
            Pane card = initializer.displayPane(PathEnum.TISSUS_CARD, t);
            cardPane.getChildren().add(card);
        }
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
        setTissus();

    }

    public void AddNewTissu(MouseEvent mouseEvent) {
    }

    public void PreviousPage(MouseEvent mouseEvent) {
    }

    public void NextPage(MouseEvent mouseEvent) {
    }
}
