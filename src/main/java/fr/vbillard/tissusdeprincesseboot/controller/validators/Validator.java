package fr.vbillard.tissusdeprincesseboot.controller.validators;

public interface Validator {

  public boolean Validate();
  public String getMessage(String... params);
}
