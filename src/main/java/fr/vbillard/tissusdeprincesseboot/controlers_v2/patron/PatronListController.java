package fr.vbillard.tissusdeprincesseboot.controlers_v2.patron;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

@Component
public class PatronListController implements IController{
	
	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
    public void AddNewTissu(MouseEvent mouseEvent) {
    }

	@FXML
    public void PreviousPage(MouseEvent mouseEvent) {
    }

	@FXML
    public void NextPage(MouseEvent mouseEvent) {
    }
	
}
