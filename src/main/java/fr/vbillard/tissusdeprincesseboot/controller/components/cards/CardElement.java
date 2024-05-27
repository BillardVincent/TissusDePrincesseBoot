package fr.vbillard.tissusdeprincesseboot.controller.components.cards;

import fr.vbillard.tissusdeprincesseboot.controller.components.style.StyleUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class CardElement extends Pane {
    public CardElement(){
        StyleUtils.style(this);
    }

    private final Label titreLbl = new Label();
    private final VBox content = new VBox();

    public void setTitre(String titre){
        titreLbl.setText(titre);
    }

    public void addContent(Pane... pane){
        for (Pane p : pane) {
            content.getChildren().add(p);
        }

    }
}
