package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.services.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@Component
@Scope("prototype")
public class ProjetEditListElementController implements IController{
	
	@FXML
	private HBox hbox;
	
	private StageInitializer initializer;
	
	private TissuRequisDto tissuRequis;
	private List<TissuUsed> lstTissus;
	private TissuUsedService tissuUsedService;
	
	public ProjetEditListElementController(TissuUsedService tissuUsedService) {
	this.tissuUsedService = tissuUsedService;
	}
	
	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
        if (data == null || data.getTissuRequis() == null || data.getProjet() == null) {
        	throw new IllegalData();
        }
        
        tissuRequis = data.getTissuRequis();
        
        lstTissus = tissuUsedService.getTissuUsedByTissuRequisAndProjet(tissuRequis, data.getProjet());
        
        
        
        setPane();
	}

	private void setPane() {
		FxData data = new FxData();
		data.setTissuRequis(tissuRequis);
		Pane tr = initializer.displayPane(PathEnum.TISSU_REQUIS, data);
		hbox.getChildren().add(tr);

		for (TissuUsed tissu : lstTissus) {
			FxData subData = new FxData();
			subData.setTissuRequis(tissuRequis);
			subData.setTissuUsed(tissu);
			Pane tu = initializer.displayPane(PathEnum.TISSU_USED_CARD, subData);
			hbox.getChildren().add(tu);
		}
		
		Pane plusCard = initializer.displayPane(PathEnum.PLUS_CARD);
		plusCard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() { 
			@Override 
			   public void handle(MouseEvent e) { 
				DevInProgressService.notImplemented(initializer);
				
				//initializer.getRoot().displaySelected(data);
			   } 
		});
		hbox.getChildren().add(plusCard);

		
	}
}
