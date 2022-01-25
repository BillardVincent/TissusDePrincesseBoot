package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;

public interface IController {

	void setStageInitializer(StageInitializer initializer, FxDto...data);

}
