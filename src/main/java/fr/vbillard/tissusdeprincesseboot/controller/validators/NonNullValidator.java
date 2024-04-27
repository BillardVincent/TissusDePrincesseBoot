package fr.vbillard.tissusdeprincesseboot.controller.validators;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Control;
import org.apache.logging.log4j.util.Strings;

public record NonNullValidator<T extends Control>(T field, String name) implements Validator {

  @Override
  public boolean validate() {
    if (field instanceof JFXComboBox<?> comboBox)
      return comboBox.getValue() != null;
    else if (field instanceof JFXTextField textField) {
      return Strings.isNotEmpty(textField.getText());
    } else {
      return false;
    }
  }

  @Override
  public String getMessage(String... params) {
    return "Le champ \"" + name + "\" doit être renseigné";
  }
}
