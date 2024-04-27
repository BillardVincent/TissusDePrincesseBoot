package fr.vbillard.tissusdeprincesseboot.controller.validators;

public interface Validator {

  boolean validate();
  String getMessage(String... params);
}
