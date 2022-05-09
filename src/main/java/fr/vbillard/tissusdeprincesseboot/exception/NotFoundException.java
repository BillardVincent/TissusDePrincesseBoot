package fr.vbillard.tissusdeprincesseboot.exception;

import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends AbstractTissuDePricesseException {

	public NotFoundException(String denomination) {
		super("Nous n'avons pas trouvé " + denomination + ".");
		alertType = Alert.AlertType.WARNING;
		title = "Non trouvé";
		header = "Non trouvé";
	}
}
