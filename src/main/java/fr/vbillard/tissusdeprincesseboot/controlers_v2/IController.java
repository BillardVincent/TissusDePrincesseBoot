package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import javafx.stage.Stage;

public interface IController {

	void setStageInitializer(StageInitializer initializer, Object...data);
}
