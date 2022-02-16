package fr.vbillard.tissusdeprincesseboot.utils;

import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class Modale {
	public Modale(Stage dialogStage, IModalController controller, FxData result) {
		// TODO Auto-generated constructor stub
	}
	private IModalController controler;
	private Stage stage;
	private FxData data;
	
}
