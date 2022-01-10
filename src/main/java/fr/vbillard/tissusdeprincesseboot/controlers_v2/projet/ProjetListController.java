package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

@Component
public class ProjetListController implements IController{
	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
    public void AddNewProjet(MouseEvent mouseEvent) {
    }

	@FXML
    public void PreviousPage(MouseEvent mouseEvent) {
    }

	@FXML
    public void NextPage(MouseEvent mouseEvent) {
    }
}
