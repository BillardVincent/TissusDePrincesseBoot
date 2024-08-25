package fr.vbillard.tissusdeprincesseboot.controller.components.aside;

import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.SearchComponent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;


public class Aside extends VBox {
    private final SelectionBox selectionBox;
    private final ScrollPane searchScrollPane = new ScrollPane();
    private Pane searchPane;

    public Aside() {
        setPrefSize(1000, 1000);
        setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        selectionBox = new SelectionBox();
        searchScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        setPadding(new Insets(0,0,5,0));
        getStyleClass().add("aside");
        getChildren().add(selectionBox);
    }

    public void addSearch(SearchComponent searchPane) {
        resetSearchPane();
        this.searchPane = searchPane;
        this.searchScrollPane.setContent(searchPane);
        getChildren().add(this.searchPane);
    }

    public void resetSearchPane() {
        if (searchPane != null) {
            getChildren().remove(searchPane);
            searchPane = null;
        }
    }

    public void addSelectionPane(Pane selectionPane) {
        resetSelectionPane();
        selectionBox.setSelectionPane(selectionPane);
        getChildren().add(this.searchPane);
    }

    public void resetSelectionPane() {
        selectionBox.resetSelectionPane();
    }

    public void setButtons(EventHandler<ActionEvent> deleteSelected, EventHandler<ActionEvent> createResearch) {
        selectionBox.setButtons(deleteSelected, createResearch);
    }
}
