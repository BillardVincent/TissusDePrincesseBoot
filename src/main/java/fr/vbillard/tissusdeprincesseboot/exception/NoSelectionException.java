package fr.vbillard.tissusdeprincesseboot.exception;

import javafx.scene.control.Alert.AlertType;

public class NoSelectionException extends AbstractTissuDePricesseException {

	public NoSelectionException(Object o, String message) {
		super(message);
		alertType = AlertType.WARNING;
		title = "Pas de selection";
		header = "Pas de " + o.getClass().getCanonicalName().toLowerCase() + " selectionné";
	}

	public NoSelectionException() {
		super("Veuillez sélectionner au moins une valeur");
		alertType = AlertType.WARNING;
		title = "Pas de selection";
		header = "Pas de valeur selectionnée";
	}

}
