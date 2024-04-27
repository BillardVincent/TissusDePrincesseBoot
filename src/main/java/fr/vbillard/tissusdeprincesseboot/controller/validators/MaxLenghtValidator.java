package fr.vbillard.tissusdeprincesseboot.controller.validators;

import com.jfoenix.controls.JFXTextField;

public record MaxLenghtValidator(JFXTextField field, String name, int size) implements Validator {

  @Override
  public boolean validate() {
    return field.getText().length() <= size;
  }

  @Override
  public String getMessage(String... params) {
    return "Le champ \"" + name + "\" doit etre inférieur à " + size + " caractères";
  }
}
