package fr.vbillard.tissusdeprincesseboot.controlers_v2.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
@Scope("prototype")
public class PlusCardController implements IController{

    protected StageInitializer initializer;
	
    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;   
    }

}
