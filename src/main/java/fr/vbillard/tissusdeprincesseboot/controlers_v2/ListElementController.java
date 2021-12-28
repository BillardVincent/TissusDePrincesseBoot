package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

@Component
public class ListElementController implements IController{

    protected StageInitializer initializer;
    
	protected void setElements() {
		
	}
	
	ListElement element;
	
	
	
    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
        setElements();
    }
}
