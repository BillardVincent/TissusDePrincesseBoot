package fr.vbillard.tissusdeprincesseboot.controller.utils;

import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.stage.Stage;

public interface IModalController{

	FxData result();

	void setStage(Stage dialogStage, FxData data);

}