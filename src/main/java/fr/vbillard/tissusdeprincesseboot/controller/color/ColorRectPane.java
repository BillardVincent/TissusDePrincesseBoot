package fr.vbillard.tissusdeprincesseboot.controller.color;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import lombok.Getter;

import static fr.vbillard.tissusdeprincesseboot.controller.color.PictureColorDialog.createHueGradient;

@Getter
class ColorRectPane extends HBox {
    private PictureColorDialog parentDialog;
    private Pane colorRect;
    private Pane colorBar;
    private Pane colorRectOverlayOne;
    private Pane colorRectOverlayTwo;
    private Region colorRectIndicator;
    private Region colorBarIndicator;
    private boolean changeIsLocal = false;
    private DoubleProperty hue = new SimpleDoubleProperty(-1.0D) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateHSBColor();
                changeIsLocal = false;
            }

        }
    };
    private DoubleProperty sat = new SimpleDoubleProperty(-1.0D) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateHSBColor();
                changeIsLocal = false;
            }

        }
    };
    private DoubleProperty bright = new SimpleDoubleProperty(-1.0D) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateHSBColor();
                changeIsLocal = false;
            }

        }
    };
    private IntegerProperty red = new SimpleIntegerProperty(-1) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateRGBColor();
                changeIsLocal = false;
            }

        }
    };
    private IntegerProperty green = new SimpleIntegerProperty(-1) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateRGBColor();
                changeIsLocal = false;
            }

        }
    };
    private IntegerProperty blue = new SimpleIntegerProperty(-1) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                updateRGBColor();
                changeIsLocal = false;
            }

        }
    };
    private DoubleProperty alpha = new SimpleDoubleProperty(100.0D) {
        protected void invalidated() {
            if (!changeIsLocal) {
                changeIsLocal = true;
                parentDialog.setCustomColor(new Color(parentDialog.getCustomColor().getRed(), parentDialog.getCustomColor().getGreen(), parentDialog.getCustomColor().getBlue(), PictureColorDialog.clamp(alpha.get() / 100.0D)));
                changeIsLocal = false;
            }

        }
    };

    private void updateRGBColor() {
        Color var1 = Color.rgb(red.get(), green.get(), blue.get(), PictureColorDialog.clamp(alpha.get() / 100.0D));
        hue.set(var1.getHue());
        sat.set(var1.getSaturation() * 100.0D);
        bright.set(var1.getBrightness() * 100.0D);
        parentDialog.setCustomColor(var1);
    }

    private void updateHSBColor() {
        Color var1 = Color.hsb(hue.get(), PictureColorDialog.clamp(sat.get() / 100.0D), PictureColorDialog.clamp(bright.get() / 100.0D), PictureColorDialog.clamp(alpha.get() / 100.0D));
        red.set(PictureColorDialog.doubleToInt(var1.getRed()));
        green.set(PictureColorDialog.doubleToInt(var1.getGreen()));
        blue.set(PictureColorDialog.doubleToInt(var1.getBlue()));
        parentDialog.setCustomColor(var1);
    }

    private void colorChanged() {
        if (!changeIsLocal) {
            changeIsLocal = true;
            hue.set(parentDialog.getCustomColor().getHue());
            sat.set(parentDialog.getCustomColor().getSaturation() * 100.0D);
            bright.set(parentDialog.getCustomColor().getBrightness() * 100.0D);
            red.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getRed()));
            green.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getGreen()));
            blue.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getBlue()));
            changeIsLocal = false;
        }

    }

    public ColorRectPane(PictureColorDialog parentDialog) {
        this.parentDialog = parentDialog;
        getStyleClass().add("color-rect-pane");
        parentDialog.customColorProperty().addListener((var1x, var2x, var3x) -> {
            colorChanged();
        });
        colorRectIndicator = new Region();
        colorRectIndicator.setId("color-rect-indicator");
        colorRectIndicator.setManaged(false);
        colorRectIndicator.setMouseTransparent(true);
        colorRectIndicator.setCache(true);
        StackPane stackPane = new StackPane();
        colorRect = new StackPane() {
            public Orientation getContentBias() {
                return Orientation.VERTICAL;
            }

            protected double computePrefWidth(double var1) {
                return var1;
            }

            protected double computeMaxWidth(double var1) {
                return var1;
            }
        };
        colorRect.getStyleClass().addAll("color-rect", "transparent-pattern");
        Pane var3 = new Pane();
        var3.backgroundProperty().bind(new ObjectBinding<Background>() {
            {
                bind(hue);
            }

            protected Background computeValue() {
                return new Background(new BackgroundFill(Color.hsb(hue.getValue(), 1.0D, 1.0D), CornerRadii.EMPTY, Insets.EMPTY));
            }
        });
        colorRectOverlayOne = new Pane();
        colorRectOverlayOne.getStyleClass().add("color-rect");
        colorRectOverlayOne.setBackground(new Background(new BackgroundFill(new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, new Stop(0.0D, Color.rgb(255, 255, 255, 1.0D)), new Stop(1.0D, Color.rgb(255, 255, 255, 0.0D))), CornerRadii.EMPTY, Insets.EMPTY)));
        EventHandler<MouseEvent> eventHandler = (var1x) -> {
            double var2 = var1x.getX();
            double var4 = var1x.getY();
            sat.set(PictureColorDialog.clamp(var2 / colorRect.getWidth()) * 100.0D);
            bright.set(100.0D - PictureColorDialog.clamp(var4 / colorRect.getHeight()) * 100.0D);
        };
        colorRectOverlayTwo = new Pane();
        colorRectOverlayTwo.getStyleClass().addAll("color-rect");
        colorRectOverlayTwo.setBackground(new Background(new BackgroundFill(new LinearGradient(0.0D, 0.0D, 0.0D, 1.0D, true, CycleMethod.NO_CYCLE, new Stop(0.0D, Color.rgb(0, 0, 0, 0.0D)), new Stop(1.0D, Color.rgb(0, 0, 0, 1.0D))), CornerRadii.EMPTY, Insets.EMPTY)));
        colorRectOverlayTwo.setOnMouseDragged(eventHandler);
        colorRectOverlayTwo.setOnMousePressed(eventHandler);
        Pane var5 = new Pane();
        var5.setMouseTransparent(true);
        var5.getStyleClass().addAll("color-rect", "color-rect-border");
        colorBar = new Pane();
        colorBar.getStyleClass().add("color-bar");
        colorBar.setBackground(new Background(new BackgroundFill(createHueGradient(), CornerRadii.EMPTY, Insets.EMPTY)));
        colorBarIndicator = new Region();
        colorBarIndicator.setId("color-bar-indicator");
        colorBarIndicator.setMouseTransparent(true);
        colorBarIndicator.setCache(true);
        colorRectIndicator.layoutXProperty().bind(sat.divide(100).multiply(colorRect.widthProperty()));
        colorRectIndicator.layoutYProperty().bind(Bindings.subtract(1, bright.divide(100)).multiply(colorRect.heightProperty()));
        colorBarIndicator.layoutYProperty().bind(hue.divide(360).multiply(colorBar.heightProperty()));
        stackPane.opacityProperty().bind(alpha.divide(100));
        EventHandler<MouseEvent> var6 = (var1x) -> {
            double var2 = var1x.getY();
            hue.set(PictureColorDialog.clamp(var2 / colorRect.getHeight()) * 360.0D);
        };
        colorBar.setOnMouseDragged(var6);
        colorBar.setOnMousePressed(var6);
        colorBar.getChildren().setAll(colorBarIndicator);
        stackPane.getChildren().setAll(var3, colorRectOverlayOne, colorRectOverlayTwo);
        colorRect.getChildren().setAll(stackPane, var5, colorRectIndicator);
        HBox.setHgrow(colorRect, Priority.SOMETIMES);
        getChildren().addAll(colorRect, colorBar);
    }

    void updateValues() {
        if (parentDialog.getCurrentColor() == null) {
            parentDialog.setCurrentColor(Color.TRANSPARENT);
        }

        changeIsLocal = true;
        hue.set(parentDialog.getCurrentColor().getHue());
        sat.set(parentDialog.getCurrentColor().getSaturation() * 100.0D);
        bright.set(parentDialog.getCurrentColor().getBrightness() * 100.0D);
        alpha.set(parentDialog.getCurrentColor().getOpacity() * 100.0D);
        parentDialog.setCustomColor(Color.hsb(hue.get(), PictureColorDialog.clamp(sat.get() / 100.0D), PictureColorDialog.clamp(bright.get() / 100.0D), PictureColorDialog.clamp(alpha.get() / 100.0D)));
        red.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getRed()));
        green.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getGreen()));
        blue.set(PictureColorDialog.doubleToInt(parentDialog.getCustomColor().getBlue()));
        changeIsLocal = false;
    }

    protected void layoutChildren() {
        super.layoutChildren();
        colorRectIndicator.autosize();
        double var1 = Math.min(colorRect.getWidth(), colorRect.getHeight());
        colorRect.resize(var1, var1);
        colorBar.resize(colorBar.getWidth(), var1);
    }
}