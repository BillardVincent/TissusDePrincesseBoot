package fr.vbillard.tissusdeprincesseboot.controller.utils;

import javafx.stage.Stage;
import lombok.Data;

@Data
public class Modale {
	public Modale(Stage dialogStage, IModalController controller, FxData result) {
	}
	private IModalController controler;
	private Stage stage;
	private FxData data;
	
}
