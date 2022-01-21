package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@Component
public class ProjetEditListElementController implements IController{
	
	@FXML
	private HBox hbox;
	
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
        lstTissus = new ArrayList<TissuUsed>();
        if (data.length==1) {
        	for (int i = 1; i< data.length; i++) {
        		 lstTissus.add((TissuUsed) data[i]);
        	}
        	
        }
        setPane();
	}

	private void setPane() {
		Pane tr = initializer.displayPane(PathEnum.TISSU_REQUIS, tissuRequis);
		hbox.getChildren().add(tr);

		for (TissuUsed tissu : lstTissus) {
			Pane tu = initializer.displayPane(PathEnum.TISSU_USED_CARD, tissu);
			hbox.getChildren().add(tu);
			
			
		}
	}
}
