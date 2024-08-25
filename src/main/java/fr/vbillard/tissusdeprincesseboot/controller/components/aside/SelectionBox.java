package fr.vbillard.tissusdeprincesseboot.controller.components.aside;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SelectionBox extends VBox {
    private Pane selectionPane = new Pane();
    private final VBox vBox;
    private final JFXButton deleteSelected = new JFXButton("Désélectionner");
    private final JFXButton createResearch = new JFXButton("Créer une recherche");


    public SelectionBox(){
        vBox = new VBox(selectionPane, new HBox(deleteSelected, createResearch));
    }

    public void setButtons(EventHandler<ActionEvent> deleteSelected, EventHandler<ActionEvent> createResearch) {
        this.deleteSelected.onActionProperty().setValue(deleteSelected);
        this.createResearch.onActionProperty().setValue(createResearch);
    }

    public void setSelectionPane(Pane selectionPane){
        this.selectionPane = selectionPane;
    }

    public void resetSelectionPane() {
        if (selectionPane != null) {
            getChildren().remove(selectionPane);
            selectionPane = null;
        }
    }

}
