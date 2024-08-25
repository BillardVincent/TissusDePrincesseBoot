package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AbstractSearchComponent extends VBox {

    private final Label label = new Label();
    private TextField field;

    public AbstractSearchComponent(String labelTxt) {
        label.setText(labelTxt + ":");
        getChildren().addAll(label, field);
    }

    public void setTextField(TextField field, Property property){

    }

}
