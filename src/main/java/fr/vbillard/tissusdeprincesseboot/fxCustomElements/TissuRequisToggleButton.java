package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import javafx.scene.control.ToggleButton;

public class TissuRequisToggleButton extends ToggleButton{

	private TissuRequisDto tissuUsed;
	
	public TissuRequisDto getTissuUsed() {
		return tissuUsed;
	}
	
	public TissuRequisToggleButton(TissuRequisDto tissuUsed) {
		this.tissuUsed = tissuUsed;
	}
}
