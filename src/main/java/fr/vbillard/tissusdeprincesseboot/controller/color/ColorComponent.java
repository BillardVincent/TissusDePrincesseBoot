package fr.vbillard.tissusdeprincesseboot.controller.color;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ColorComponent extends HBox {

    @Getter
    SimpleObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    StageInitializer initializer;
    Image image;
    JFXButton btn = new JFXButton();
    Rectangle rectangle = new Rectangle(20, 20, Color.TRANSPARENT);

    public ColorComponent(StageInitializer initializer, Color color, Image image){
       initialize(initializer, color, image);
    }

    public void initialize (StageInitializer initializer, Color color, Image image){
        this.initializer = initializer;
        if (color != null) {
            colorProperty.set(color);
        }
        this.image = image;

        setColorButton();

        btn.setOnAction(e -> {
            PictureColorDialog dialog = new PictureColorDialog(initializer.getPrimaryStage(), image);
            dialog.setCurrentColor(colorProperty.get());
            dialog.setOnSave(() -> setColor(dialog.getCustomColor()));
            dialog.show();

        });

        getChildren().addAll(btn);
    }

    private void setColorButton() {
        HBox content = new HBox();
        content.setSpacing(5);
        content.setAlignment(Pos.CENTER);
        if (colorProperty == null || colorProperty.get() == null) {
             content.getChildren().addAll(GlyphIconUtil.palette(), GlyphIconUtil.chevronDown());
        } else {
            rectangle.setFill(colorProperty.get());
            content.getChildren().addAll(rectangle, GlyphIconUtil.chevronDown());
        }
        btn.setGraphic(content);
    }

    private void setColor(Color color){
        colorProperty.set(color);
        setColorButton();
    }

    public void setImage(Image image){
        this.image = image;
    }

    public  Color getColor(){
        return Color.TRANSPARENT.equals(colorProperty.get())? null : colorProperty.get();
    }
}
