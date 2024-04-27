package fr.vbillard.tissusdeprincesseboot.controller.validators;

import com.jfoenix.controls.JFXTextField;

public record MaxLenghtFloatValidator(JFXTextField field, String name) implements Validator {

  private static final String REGEX = "^\\d{1,6}(\\.\\d{1,3})?$";
  @Override
  public boolean validate() {
    return field.getText().isEmpty() || field.getText().matches(REGEX);
  }

  @Override
  public String getMessage(String... params) {
    return "Le champ \"" + name + "\" doit etre inférieur à 999 999 et avoir entre 0 et 2 chiffres après la virgule";
  }
}
