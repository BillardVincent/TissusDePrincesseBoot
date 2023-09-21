package fr.vbillard.tissusdeprincesseboot.controller.color;

import com.sun.javafx.scene.control.Properties;
import com.sun.javafx.scene.control.WebColorField;
import com.sun.javafx.util.Utils;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.*;
import lombok.Getter;

@Getter
class PictureColorDialog extends HBox {
    private final Stage dialog = new Stage();
    private ColorRectPane colorRectPane;
    private ControlsPane controlsPane;
    private ObjectProperty<Color> currentColorProperty;
    private ObjectProperty<Color> customColorProperty;
    private Runnable onSave;
    private Runnable onUse;
    private Runnable onCancel;
    private WebColorField webField;
    private Scene customScene;
    private String saveBtnText;
    private boolean showUseBtn;
    private boolean showOpacitySlider;
    private final EventHandler<KeyEvent> keyEventListener;
    private InvalidationListener positionAdjuster;
    private Image image;
    private PictureRectPane pictureRectPane;

    void setWebField(WebColorField webField) {
        this.webField = webField;
    }

    PictureColorDialog(Window var1, Image image) {
        this.image = image;
        currentColorProperty = new SimpleObjectProperty(Color.WHITE);
        customColorProperty = new SimpleObjectProperty(Color.TRANSPARENT);
        webField = null;
        showUseBtn = true;
        showOpacitySlider = true;
        keyEventListener = (var1x) -> {
            switch (var1x.getCode()) {
                case ESCAPE:
                    dialog.setScene((Scene) null);
                    dialog.close();
                default:
            }
        };
        positionAdjuster = var11 -> {
            if (!Double.isNaN(dialog.getWidth()) && !Double.isNaN(dialog.getHeight())) {
                dialog.widthProperty().removeListener(positionAdjuster);
                dialog.heightProperty().removeListener(positionAdjuster);
                fixPosition();
            }
        };
        getStyleClass().add("custom-color-dialog");
        if (var1 != null) {
            dialog.initOwner(var1);
        }

        dialog.setTitle(Properties.getColorPickerString("customColorDialogTitle"));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setResizable(false);
        dialog.addEventHandler(KeyEvent.ANY, keyEventListener);
        customScene = new Scene(this);
        Scene var2 = var1.getScene();
        if (var2 != null) {
            if (var2.getUserAgentStylesheet() != null) {
                customScene.setUserAgentStylesheet(var2.getUserAgentStylesheet());
            }

            customScene.getStylesheets().addAll(var2.getStylesheets());
        }

        buildUI();
        dialog.setScene(customScene);
    }

    void buildUI() {
        colorRectPane = new ColorRectPane(this);
        controlsPane = new ControlsPane(this);
        pictureRectPane = new PictureRectPane(this, image);

        setHgrow(controlsPane, Priority.ALWAYS);
        getChildren().setAll(pictureRectPane, colorRectPane, controlsPane);
    }

    void setCurrentColor(Color var1) {
        currentColorProperty.set(var1);
    }

    final Color getCurrentColor() {
        return currentColorProperty.get();
    }

    final ObjectProperty<Color> customColorProperty() {
        return customColorProperty;
    }

    final void setCustomColor(Color var1) {
        customColorProperty.set(var1);
    }

    final Color getCustomColor() {
        return customColorProperty.get();
    }

    Runnable getOnSave() {
        return onSave;
    }

    void setOnSave(Runnable var1) {
        onSave = var1;
    }

    void setSaveBtnToOk() {
        saveBtnText = Properties.getColorPickerString("OK");
        buildUI();
    }

    Runnable getOnUse() {
        return onUse;
    }

    void setOnUse(Runnable var1) {
        onUse = var1;
    }

    void setShowUseBtn(boolean var1) {
        showUseBtn = var1;
        buildUI();
    }

    void setShowOpacitySlider(boolean var1) {
        showOpacitySlider = var1;
        buildUI();
    }

    Runnable getOnCancel() {
        return onCancel;
    }

    void setOnCancel(Runnable var1) {
        onCancel = var1;
    }

    void setOnHidden(EventHandler<WindowEvent> var1) {
        dialog.setOnHidden(var1);
    }

    Stage getDialog() {
        return dialog;
    }

    void show() {
        if (dialog.getOwner() != null) {
            dialog.widthProperty().addListener(positionAdjuster);
            dialog.heightProperty().addListener(positionAdjuster);
            positionAdjuster.invalidated(null);
        }

        if (dialog.getScene() == null) {
            dialog.setScene(customScene);
        }

        colorRectPane.updateValues();
        pictureRectPane.updateValues();
        dialog.show();
    }

    void hide() {
        if (dialog.getOwner() != null) {
            dialog.hide();
        }

    }

    void fixPosition() {
        Window var1 = dialog.getOwner();
        Screen var2 = Utils.getScreen(var1);
        Rectangle2D var3 = var2.getBounds();
        double var4 = var1.getX() + var1.getWidth();
        double var6 = var1.getX() - dialog.getWidth();
        double var8;
        if (var3.getMaxX() >= var4 + dialog.getWidth()) {
            var8 = var4;
        } else if (var3.getMinX() <= var6) {
            var8 = var6;
        } else {
            var8 = Math.max(var3.getMinX(), var3.getMaxX() - dialog.getWidth());
        }

        double var10 = Math.max(var3.getMinY(), Math.min(var3.getMaxY() - dialog.getHeight(), var1.getY()));
        dialog.setX(var8);
        dialog.setY(var10);
    }

    public void layoutChildren() {
        super.layoutChildren();
        if (dialog.getMinWidth() <= 0.0D || dialog.getMinHeight() <= 0.0D) {
            double var1 = Math.max(0.0D, computeMinWidth(getHeight()) + (dialog.getWidth() - customScene.getWidth()));
            double var3 = Math.max(0.0D, computeMinHeight(getWidth()) + (dialog.getHeight() - customScene.getHeight()));
            dialog.setMinWidth(var1);
            dialog.setMinHeight(var3);
        }
    }

    static double clamp(double var0) {
        return var0 < 0.0D ? 0.0D : (Math.min(var0, 1.0D));
    }

    static LinearGradient createHueGradient() {
        Stop[] var2 = new Stop[255];

        for (int var3 = 0; var3 < 255; ++var3) {
            double var0 = 1.0D - 0.00392156862745098D * var3;
            int var4 = (int) (var3 / 255.0D * 360.0D);
            var2[var3] = new Stop(var0, Color.hsb(var4, 1.0D, 1.0D));
        }

        return new LinearGradient(0.0D, 1.0D, 0.0D, 0.0D, true, CycleMethod.NO_CYCLE, var2);
    }

    static int doubleToInt(double var0) {
        return (int) (var0 * 255.0D + 0.5D);
    }
}


