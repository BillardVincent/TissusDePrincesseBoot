package fr.vbillard.tissusdeprincesseboot.controller.color;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorComponent extends HBox {

    SimpleObjectProperty<Color> colorProperty;
    StageInitializer initializer;
    Image image;
    JFXButton btn = new JFXButton();
    Rectangle rectangle = new Rectangle(20, 20, Color.GREEN);

    public ColorComponent(StageInitializer initializer, Color color, Image image){
        this.initializer = initializer;
        colorProperty.set(color);
        this.image = image;

        setColorButton();

        btn.setOnAction(e -> {
            PictureColorDialog dialog = new PictureColorDialog(initializer.getPrimaryStage(), image);
            dialog.setShowUseBtn(false);
            dialog.setOnSave(() -> {
                setColor(dialog.getCustomColor());
            });
            dialog.show();

        });

        getChildren().addAll(rectangle, btn);
    }

    private void setColorButton() {
        if (colorProperty == null || colorProperty.get() == null) {
            btn.setGraphic( GlyphIconUtil.palette());
        } else {
            rectangle.setFill(colorProperty.get());
        }
    }

    private void setColor(Color color){
        colorProperty.set(color);
        setColorButton();
    }

    public void setImage(Image image){
        this.image = image;
    }
}
