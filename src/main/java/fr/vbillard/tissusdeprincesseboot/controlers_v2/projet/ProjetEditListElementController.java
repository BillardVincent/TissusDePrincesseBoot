package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.fxml.FXML;

@Component
public class ProjetEditListElementController implements IController{
	
	private StageInitializer initializer;
	
	private TissuRequisDto tissuRequis;
	private List<TissuUsed> lstTissus;
	
	public ProjetEditListElementController() {
	}
	
	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		this.initializer = initializer;
        if (data.length > 0 && data[0] instanceof TissuRequisDto){
        	tissuRequis = (TissuRequisDto) data[0];
        }
        if (data.length>1) {
        	for (int i = 1; i< data.length; i++) {
        		 lstTissus.add((TissuUsed) data[i]);
        	}
        }
        setPane();
	}

	private void setPane() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
