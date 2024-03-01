package fr.vbillard.tissusdeprincesseboot.exception;

public class UnexpectedException extends AbstractTissuDePricesseException{
    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(){
        this("Une erreur inattendue est survenue");
    }
}
