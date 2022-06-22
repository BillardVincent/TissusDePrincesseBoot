package fr.vbillard.tissusdeprincesseboot.fx_custom_element;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
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
