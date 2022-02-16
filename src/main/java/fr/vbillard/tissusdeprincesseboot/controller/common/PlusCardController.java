package fr.vbillard.tissusdeprincesseboot.controller.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;

@Component
@Scope("prototype")
public class PlusCardController implements IController{

    protected StageInitializer initializer;
	
    @Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;   
    }

}
