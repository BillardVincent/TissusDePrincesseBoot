package fr.vbillard.tissusdeprincesseboot.utils;

import fr.vbillard.tissusdeprincesseboot.controlers_v2.IModalController;
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
