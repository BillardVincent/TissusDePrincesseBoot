package fr.vbillard.tissusdeprincesseboot.controller.validators;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class ValidatorUtils {

  private ValidatorUtils(){}

  private static final String SEPARATOR = "\n";

  public static String StringBuilder(List<Validator> validators) {
    return validateWithMessage(validators.toArray(new Validator[0]));
  }

  public static String validateWithMessage(Validator... validators) {
    StringBuilder result = new StringBuilder(Strings.EMPTY);
    for (Validator v : validators) {
      if (!v.validate()) {
        result.append(v.getMessage()).append(SEPARATOR);
      }
    }
    return result.toString();
  }

  public static boolean validate(List<Validator> validators) {
    return validate(validators.toArray(new Validator[0]));
  }

    public static boolean validate(Validator... validators) {
    for (Validator v : validators) {
      if (!v.validate()) {
        return false;
      }
    }
    return true;
  }

  public static boolean areValidatorsValid(StageInitializer initializer, Validator... validators) {
    String errorMessage = validateWithMessage(validators);

    if (errorMessage.isEmpty()) {
      return true;
    } else {
      ShowAlert.erreur(initializer.getPrimaryStage(), "Champ(s) invalide(s)", "Merci de corriger :", errorMessage);
      return false;
    }
  }
}

