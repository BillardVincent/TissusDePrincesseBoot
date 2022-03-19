package fr.vbillard.tissusdeprincesseboot.exception;

import javafx.scene.control.Alert;

public class CantBeDeletedException extends AbstractTissuDePricesseException {


    public CantBeDeletedException(Object o, String message) {
        super(message);
        alertType = Alert.AlertType.WARNING;
        title = o.toString() + "ne peut pas être supprimé";
        header = o.toString() + "ne peut pas être supprimé. Il est utilisé pour caractériser des tissus";
    }
}