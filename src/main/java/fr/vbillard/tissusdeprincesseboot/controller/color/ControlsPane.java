package fr.vbillard.tissusdeprincesseboot.controller.color;

import com.sun.javafx.scene.control.IntegerField;
import com.sun.javafx.scene.control.Properties;
import com.sun.javafx.scene.control.WebColorField;
import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import com.sun.javafx.scene.control.skin.WebColorFieldSkin;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ControlsPane extends VBox {
    private Label currentColorLabel;
    private Label newColorLabel;
    private Region currentColorRect;
    private Region newColorRect;
    private Region currentTransparent;
    private GridPane currentAndNewColor;
    private Region currentNewColorBorder;
    private ToggleButton hsbButton;
    private ToggleButton rgbButton;
    private ToggleButton webButton;
    private HBox hBox;
    private Label[] labels = new Label[4];
    private Slider[] sliders = new Slider[4];
    private IntegerField[] fields = new IntegerField[4];
    private Label[] units = new Label[4];
    private HBox buttonBox;
    private Region whiteBox;
    private GridPane settingsPane = new GridPane();
    private Property<Number>[] bindedProperties = new Property[4];
    private PictureColorDialog parentDialog;

    ControlsPane(PictureColorDialog parentDialog) {
        this.parentDialog = parentDialog;
        getStyleClass().add("controls-pane");
        currentNewColorBorder = new Region();
        currentNewColorBorder.setId("current-new-color-border");
        currentTransparent = new Region();
        currentTransparent.getStyleClass().addAll("transparent-pattern");
        currentColorRect = new Region();
        currentColorRect.getStyleClass().add("color-rect");
        currentColorRect.setId("current-color");
        currentColorRect.backgroundProperty().bind(new ObjectBinding<>() {
            {
                bind(parentDialog.getCurrentColorProperty());
            }

            protected Background computeValue() {
                return new Background(new BackgroundFill(parentDialog.getCurrentColorProperty().get(), CornerRadii.EMPTY, Insets.EMPTY));
            }
        });
        newColorRect = new Region();
        newColorRect.getStyleClass().add("color-rect");
        newColorRect.setId("new-color");
        newColorRect.backgroundProperty().bind(new ObjectBinding<Background>() {
            {
                bind(parentDialog.getCustomColorProperty());
            }

            protected Background computeValue() {
                return new Background(new BackgroundFill(parentDialog.getCustomColorProperty().get(), CornerRadii.EMPTY, Insets.EMPTY));
            }
        });
        currentColorLabel = new Label(Properties.getColorPickerString("currentColor"));
        newColorLabel = new Label(Properties.getColorPickerString("newColor"));
        whiteBox = new Region();
        whiteBox.getStyleClass().add("customcolor-controls-background");
        hsbButton = new ToggleButton(Properties.getColorPickerString("colorType.hsb"));
        hsbButton.getStyleClass().add("left-pill");
        rgbButton = new ToggleButton(Properties.getColorPickerString("colorType.rgb"));
        rgbButton.getStyleClass().add("center-pill");
        webButton = new ToggleButton(Properties.getColorPickerString("colorType.web"));
        webButton.getStyleClass().add("right-pill");
        ToggleGroup var2 = new ToggleGroup();
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(hsbButton, rgbButton, webButton);
        Region var3 = new Region();
        var3.setId("spacer1");
        Region var4 = new Region();
        var4.setId("spacer2");
        Region var5 = new Region();
        var5.setId("spacer-side");
        Region var6 = new Region();
        var6.setId("spacer-side");
        Region var7 = new Region();
        var7.setId("spacer-bottom");
        currentAndNewColor = new GridPane();
        currentAndNewColor.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints());
        currentAndNewColor.getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);
        currentAndNewColor.getColumnConstraints().get(1).setHgrow(Priority.ALWAYS);
        currentAndNewColor.getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints());
        currentAndNewColor.getRowConstraints().get(2).setVgrow(Priority.ALWAYS);
        VBox.setVgrow(currentAndNewColor, Priority.ALWAYS);
        currentAndNewColor.getStyleClass().add("current-new-color-grid");
        currentAndNewColor.add(currentColorLabel, 0, 0);
        currentAndNewColor.add(newColorLabel, 1, 0);
        currentAndNewColor.add(var3, 0, 1, 2, 1);
        currentAndNewColor.add(currentTransparent, 0, 2, 2, 1);
        currentAndNewColor.add(currentColorRect, 0, 2);
        currentAndNewColor.add(newColorRect, 1, 2);
        currentAndNewColor.add(currentNewColorBorder, 0, 2, 2, 1);
        currentAndNewColor.add(var4, 0, 3, 2, 1);
        settingsPane = new GridPane();
        settingsPane.setId("settings-pane");
        settingsPane.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
        settingsPane.getColumnConstraints().get(0).setHgrow(Priority.NEVER);
        settingsPane.getColumnConstraints().get(2).setHgrow(Priority.ALWAYS);
        settingsPane.getColumnConstraints().get(3).setHgrow(Priority.NEVER);
        settingsPane.getColumnConstraints().get(4).setHgrow(Priority.NEVER);
        settingsPane.getColumnConstraints().get(5).setHgrow(Priority.NEVER);
        settingsPane.add(whiteBox, 0, 0, 6, 5);
        settingsPane.add(hBox, 0, 0, 6, 1);
        settingsPane.add(var5, 0, 0);
        settingsPane.add(var6, 5, 0);
        settingsPane.add(var7, 0, 4);
        WebColorField webField = new WebColorField();
        webField.getStyleClass().add("web-field");
        webField.setSkin(new WebColorFieldSkin(webField));
        webField.valueProperty().bindBidirectional(parentDialog.getCustomColorProperty());
        webField.visibleProperty().bind(var2.selectedToggleProperty().isEqualTo(webButton));
        parentDialog.setWebField(webField);
        settingsPane.add(webField, 2, 1);

        for(int var8 = 0; var8 < 4; ++var8) {
            labels[var8] = new Label();
            labels[var8].getStyleClass().add("settings-label");
            sliders[var8] = new Slider();
            fields[var8] = new IntegerField();
            fields[var8].getStyleClass().add("color-input-field");
            fields[var8].setSkin(new IntegerFieldSkin(fields[var8]));
            units[var8] = new Label(var8 == 0 ? "°" : "%");
            units[var8].getStyleClass().add("settings-unit");
            if (var8 > 0 && var8 < 3) {
                labels[var8].visibleProperty().bind(var2.selectedToggleProperty().isNotEqualTo(webButton));
            }

            if (var8 < 3) {
                sliders[var8].visibleProperty().bind(var2.selectedToggleProperty().isNotEqualTo(webButton));
                fields[var8].visibleProperty().bind(var2.selectedToggleProperty().isNotEqualTo(webButton));
                units[var8].visibleProperty().bind(var2.selectedToggleProperty().isEqualTo(hsbButton));
            }

            int var9 = 1 + var8;
            if (var8 == 3) {
                ++var9;
            }

            if (var8 != 3 || parentDialog.isShowOpacitySlider()) {
                settingsPane.add(labels[var8], 1, var9);
                settingsPane.add(sliders[var8], 2, var9);
                settingsPane.add(fields[var8], 3, var9);
                settingsPane.add(units[var8], 4, var9);
            }
        }

        set(3, Properties.getColorPickerString("opacity_colon"), 100, new SimpleDoubleProperty(100));
        hsbButton.setToggleGroup(var2);
        rgbButton.setToggleGroup(var2);
        webButton.setToggleGroup(var2);
        var2.selectedToggleProperty().addListener((var2x, var3x, var4x) -> {
            if (var4x == null) {
                var2.selectToggle(var3x);
            } else if (var4x == hsbButton) {
                showHSBSettings();
            } else if (var4x == rgbButton) {
                showRGBSettings();
            } else {
                showWebSettings();
            }

        });
        var2.selectToggle(hsbButton);
        buttonBox = new HBox();
        buttonBox.setId("buttons-hbox");
        Button var11 = new Button(parentDialog.getSaveBtnText() != null && !parentDialog.getSaveBtnText().isEmpty() ? parentDialog.getSaveBtnText() : Properties.getColorPickerString("Save"));
        var11.setDefaultButton(true);
        var11.setOnAction((var1x) -> {
            if (parentDialog.getOnSave() != null) {
                parentDialog.getOnSave().run();
            }

            parentDialog.getDialog().hide();
        });
        
        Button var10 = new Button(Properties.getColorPickerString("Cancel"));
        var10.setCancelButton(true);
        var10.setOnAction((var1x) -> {
            parentDialog.getCustomColorProperty().set(parentDialog.getCurrentColor());
            if (parentDialog.getOnCancel() != null) {
                parentDialog.getOnCancel().run();
            }

            parentDialog.getDialog().hide();
        });
        
        
        buttonBox.getChildren().addAll(var11, var10);
        

        getChildren().addAll(currentAndNewColor, settingsPane, buttonBox);
    }

    private void showHSBSettings() {
        set(0, Properties.getColorPickerString("hue_colon"), 360, parentDialog.getColorRectPane().getHue());
        set(1, Properties.getColorPickerString("saturation_colon"), 100, parentDialog.getColorRectPane().getSat());
        set(2, Properties.getColorPickerString("brightness_colon"), 100, parentDialog.getColorRectPane().getBright());
    }

    private void showRGBSettings() {
        set(0, Properties.getColorPickerString("red_colon"), 255, parentDialog.getColorRectPane().getRed());
        set(1, Properties.getColorPickerString("green_colon"), 255, parentDialog.getColorRectPane().getGreen());
        set(2, Properties.getColorPickerString("blue_colon"), 255, parentDialog.getColorRectPane().getBlue());
    }

    private void showWebSettings() {
        labels[0].setText(Properties.getColorPickerString("web_colon"));
    }

    private void set(int var1, String var2, int var3, Property<Number> var4) {
        labels[var1].setText(var2);
        if (bindedProperties[var1] != null) {
            sliders[var1].valueProperty().unbindBidirectional(bindedProperties[var1]);
            fields[var1].valueProperty().unbindBidirectional(bindedProperties[var1]);
        }

        sliders[var1].setMax(var3);
        sliders[var1].valueProperty().bindBidirectional(var4);
        labels[var1].setLabelFor(sliders[var1]);
        fields[var1].setMaxValue(var3);
        fields[var1].valueProperty().bindBidirectional(var4);
        bindedProperties[var1] = var4;
    }
}
