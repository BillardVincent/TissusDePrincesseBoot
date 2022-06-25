package fr.vbillard.tissusdeprincesseboot.fx_custom_element;

import javafx.scene.control.CheckBox;
import lombok.Data;

@Data
public class ComboCheckBoxItem<T> {
	private CheckBox checkbox;
	private T string;

}
