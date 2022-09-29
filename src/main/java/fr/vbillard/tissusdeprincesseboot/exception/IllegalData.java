package fr.vbillard.tissusdeprincesseboot.exception;

public class IllegalData extends AbstractTissuDePricesseException{

	public IllegalData (String message) {
		super(message);	}
	public IllegalData () {
		super("Il y a des données erronées");	}

}
