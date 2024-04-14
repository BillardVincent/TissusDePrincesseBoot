package fr.vbillard.tissusdeprincesseboot.controller.validators;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import org.apache.logging.log4j.util.Strings;

public class ValidatorUtils {

  private ValidatorUtils(){}

  private static final String SEPARATOR = "\n";

  private static String validate(Validator... validators) {
    StringBuilder result = new StringBuilder(Strings.EMPTY);
    for (Validator v : validators) {
      if (!v.Validate()) {
        result.append(v.getMessage()).append(SEPARATOR);
      }
    }
    return result.toString();
  }

  public static boolean areValidatorsValid(StageInitializer initializer, Validator... validators) {
    String errorMessage = validate(validators);

    if (errorMessage.isEmpty()) {
      return true;
    } else {
      ShowAlert.erreur(initializer.getPrimaryStage(), "Champ(s) invalide(s)", "Merci de corriger :", errorMessage);
      return false;
    }
  }
}

