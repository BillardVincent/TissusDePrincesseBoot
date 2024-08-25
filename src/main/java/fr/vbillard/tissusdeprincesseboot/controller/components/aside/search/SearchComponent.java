package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.components.style.StyleUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.data.jpa.domain.Specification;

public abstract class SearchComponent<T> extends VBox {

    protected VBox content = new VBox(5);
    protected Specification<T> specification;

    protected SearchComponent(Specification<T> specification){
        this.specification = specification;
        Label rechercheLbl = new Label("Recherche");
        StyleUtils.styleTitle2(rechercheLbl);

        JFXButton cancelBtn = getCancelButton();
        JFXButton rechercheBtn = getRechercheButton();

        HBox buttonBar = new HBox(cancelBtn, rechercheBtn);

        getChildren().addAll(rechercheLbl, buttonBar, content);

    }

    protected void addContent(Node pane){
        content.getChildren().add(pane);
    }

    protected abstract JFXButton getRechercheButton();

    protected abstract JFXButton getCancelButton();
}
