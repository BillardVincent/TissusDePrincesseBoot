package fr.vbillard.tissusdeprincesseboot.controller.validators;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Control;
import org.apache.logging.log4j.util.Strings;

public class NonNullValidator<T extends Control> implements Validator {

  private T field;
  private String name;

  public NonNullValidator(T field, String name){
    this.field = field;
    this.name = name;
  }

  @Override
  public boolean Validate() {
    if (field instanceof JFXComboBox)
    return ((JFXComboBox)field).getValue() != null;
    else if(field instanceof JFXTextField){
      return Strings.isNotEmpty(((JFXTextField) field).getText());
    }else{
      return false;
    }
  }

  @Override
  public String getMessage(String... params) {
    return "Le champ \"" + name + "\" doit être renseigné";
  }
}
