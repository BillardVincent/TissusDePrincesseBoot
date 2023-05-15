package fr.vbillard.tissusdeprincesseboot.controller.components;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;

public abstract class CustomSpinner<T extends Number> extends JFXTextField {

  protected CustomSpinner(){
    setTextFormatter(getFormatter());
    setAlignment(Pos.CENTER_RIGHT);
    setSpinnerFocused();
  }

  public abstract TextFormatter<T> getFormatter();

  public void setSpinnerFocused(){
    focusedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
      if (isFocused() && !getText().isEmpty()) {
        selectAll();
      }
    }));
  }

}
