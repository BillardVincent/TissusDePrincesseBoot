package fr.vbillard.tissusdeprincesseboot.exception;

import javafx.scene.control.Alert.AlertType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractTissuDePricesseException extends RuntimeException{
	protected AlertType alertType;
	protected String title ;
	protected String header;
	protected String content;
	
	public AbstractTissuDePricesseException(String message) {
		super(message);
		content = message;
	}
}
