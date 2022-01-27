package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

@Component
public abstract class ViewListController implements IController{

    @FXML
    protected TilePane cardPane;
    @FXML
    protected FontAwesomeIconView previousIcon;
    @FXML
    protected FontAwesomeIconView nextIcon;
    @FXML
    protected Label start;
    @FXML
    protected Label end;
    @FXML
    protected Label total;
    
    protected StageInitializer initializer;

    protected int page;
    protected final static int PAGE_SIZE = 10;
    
    
	protected abstract void setElements();
	
    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        page = 0;
        this.initializer = initializer;
        setElements();
    }

   
    public abstract void AddNewElement(MouseEvent mouseEvent);

    public void PreviousPage(MouseEvent mouseEvent) {
        if (page > 0) {
            page --;
        }
        setElements();
    }

    public void NextPage(MouseEvent mouseEvent) {
        page ++;
        setElements();
    }
}
