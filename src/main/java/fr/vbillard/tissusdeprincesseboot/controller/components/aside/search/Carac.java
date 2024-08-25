package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class Carac extends Accordion {

    private final VBox content = new VBox();
    public Carac(String nom) {
        TitledPane pane = new TitledPane();
        pane.setText(nom);
        getPanes().add(pane);
        pane.setContent(content);

        content.setSpacing(5);
        content.setAlignment(Pos.CENTER_LEFT);

    }

    public void addContent(AbstractSearchComponent n){
        content.getChildren().add(n);
    }


}
