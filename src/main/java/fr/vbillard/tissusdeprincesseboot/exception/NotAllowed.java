package fr.vbillard.tissusdeprincesseboot.exception;

import javafx.scene.control.Alert;

public class NotAllowed extends AbstractTissuDePricesseException {

	public NotAllowed() {
		super("Action non autorisée");
		alertType = Alert.AlertType.WARNING;
		title = "Non autorisé";
		header = "Non autorisé";
	}

}
