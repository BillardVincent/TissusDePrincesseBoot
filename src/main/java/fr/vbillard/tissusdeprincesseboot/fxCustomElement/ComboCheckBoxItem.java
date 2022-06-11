package fr.vbillard.tissusdeprincesseboot.fxCustomElement;

import javafx.scene.control.CheckBox;
import lombok.Data;

@Data
public class ComboCheckBoxItem<T> {
	private CheckBox checkbox;
	private T string;

}
