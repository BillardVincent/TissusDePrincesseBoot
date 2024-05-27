package fr.vbillard.tissusdeprincesseboot.controller.components.cards;

import fr.vbillard.tissusdeprincesseboot.controller.components.style.StyleUtils;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class Card extends VBox {

    public Card(){
        StyleUtils.style(this);
        getChildren().addAll(titreLbl, sousTitreBox,image, content, footer);
    }

    private final Label titreLbl = new Label();
    private final VBox titreBox = new VBox(titreLbl);
    private final VBox sousTitreBox = new VBox();
    private final VBox content = new VBox();
    private final ImageView image = new ImageView();
    private final VBox imageBox = new VBox(image);
    private final VBox footer = new VBox();


    public void setTitre(String titre){
        titreLbl.setText(titre);
        titreLbl.setPrefHeight(30);
        titreBox.setPrefWidth(Double.MAX_VALUE);
    }

    public void addSousTitre(String sousTitre){
        Label sousTitreLbl = new Label(sousTitre);
        StyleUtils.styleTitle3(sousTitreLbl);
        sousTitreLbl.prefWidthProperty().bind(widthProperty());
        sousTitreBox.getChildren().add(sousTitreLbl);
    }

    public void addContent(boolean withSeparator, Pane... pane){
        for (Pane p : pane) {
            if (withSeparator && !content.getChildren().isEmpty()) {
                content.getChildren().add(new Separator(Orientation.HORIZONTAL));
            }
            content.getChildren().add(p);
        }
    }

    public void addContent(Pane... pane){
        addContent(false, pane);
    }
}
