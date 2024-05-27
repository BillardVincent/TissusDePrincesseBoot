package fr.vbillard.tissusdeprincesseboot.controller.components;

import fr.vbillard.tissusdeprincesseboot.controller.components.style.StyleUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;

@Getter
public class LabelValue  extends HBox {

    private Label label;
    private Label value;

    public LabelValue(){
        StyleUtils.style(this);
    }

}
