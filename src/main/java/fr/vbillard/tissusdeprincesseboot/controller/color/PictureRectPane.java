package fr.vbillard.tissusdeprincesseboot.controller.color;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PictureRectPane extends HBox {
    private Pane colorRectOverlayOne;

    private Region colorRectIndicator;
    private boolean changeIsLocal = false;
    private PictureColorDialog parentDialog;
    private Pane colorRect;


    PictureRectPane(PictureColorDialog parentDialog, Image image) {
        this.parentDialog = parentDialog;
        getStyleClass().add("color-rect-pane");
        parentDialog.customColorProperty().addListener((var1x, var2x, var3x) -> {
            colorRectIndicator.setVisible(changeIsLocal);
            changeIsLocal = false;
        });
        colorRectIndicator = new Region();
        colorRectIndicator.setId("color-rect-indicator");
        colorRectIndicator.setManaged(false);
        colorRectIndicator.setMouseTransparent(true);
        colorRectIndicator.setCache(true);
        StackPane stackPane = new StackPane();
        colorRect = new StackPane() {
            @Override
            public Orientation getContentBias() {
                return Orientation.VERTICAL;
            }

            @Override
            protected double computePrefWidth(double var1) {
                return var1;
            }

            @Override
            protected double computeMaxWidth(double var1) {
                return var1;
            }
        };
        colorRect.getStyleClass().addAll("color-rect", "transparent-pattern");
        Pane var3 = new Pane();

        colorRectOverlayOne = new Pane();
        colorRectOverlayOne.getStyleClass().add("color-rect");
        colorRectOverlayOne.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,  new BackgroundSize(1.0, 1.0, true, true, false, false))));
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            changeIsLocal = true;
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            colorRectIndicator.setVisible(true);
            colorRectIndicator.setLayoutX(mouseEvent.getX());
            colorRectIndicator.setLayoutY(mouseEvent.getY());

            int xImage = (int)Math.round(image.getWidth() * (x/getWidth()));
            int yImage = (int)Math.round(image.getHeight() * (y/getHeight()));

            Color color = image.getPixelReader().getColor(xImage, yImage);
            parentDialog.setCustomColor(color);

        };

        colorRectOverlayOne.setOnMousePressed(eventHandler);
        colorRectOverlayOne.setOnMouseDragged(eventHandler);
        stackPane.getChildren().setAll(var3, colorRectOverlayOne);
        colorRect.getChildren().setAll(stackPane, colorRectIndicator);
        HBox.setHgrow(colorRect, Priority.SOMETIMES);
        getChildren().addAll(colorRect);
    }

    void updateValues() {
        if (parentDialog.getCurrentColor() == null) {
            parentDialog.setCurrentColor(Color.TRANSPARENT);
        }

        colorRectIndicator.setVisible(changeIsLocal);
        changeIsLocal = false;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        colorRectIndicator.autosize();
        double var1 = Math.min(colorRect.getWidth(), colorRect.getHeight());
        colorRect.resize(var1, var1);
    }

}
