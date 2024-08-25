package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.data.jpa.domain.Specification;

public class FournitureSearchComponent extends SearchComponent<Fourniture> {
    private final Pane type = new Pane();
    public FournitureSearchComponent(Specification specification) {
        super(specification);
        // TODO
        type.setPrefSize(20, 20);
        type.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        addContent(type);

    }

    public void setCaracCheckBox(CaracChkBox checkBox){
        type.getChildren().add(checkBox);
    }

    @Override
    protected JFXButton getRechercheButton() {
        JFXButton btn = new JFXButton("Lancer la recherche");
        return btn;
    }

    @Override
    protected JFXButton getCancelButton() {
        JFXButton btn = new JFXButton("Annuler");
        return btn;
    }
}
