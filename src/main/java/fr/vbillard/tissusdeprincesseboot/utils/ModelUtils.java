package fr.vbillard.tissusdeprincesseboot.utils;

public class ModelUtils {
	
	private final static String VOYELLES = "aeiouy";
	
	public static String generateString(EntityToString entity, Articles article) {
		return generateString(entity, article, false, false);
	}
	
	public static String generateString(EntityToString entity, Articles article, boolean pluriel, boolean withMaj) {
		String result = "";
		switch (article) {
		case AUCUN:
			break;
		case DEFINI:
			if (pluriel) result += "les ";
			else if (VOYELLES.contains(entity.getLabel().substring(0, 1))) result += "l'";
			else if (entity.isMasculin()) result += "le ";
			else result += "la ";
			break;
		case INDEFINI:
			if (pluriel) result += "des ";
			else if (entity.isMasculin()) result += "un ";
			else result += "une ";
			break;
		case PARTITIF:
			if (pluriel) result += "des ";
			else if (VOYELLES.contains(entity.getLabel().substring(0, 1))) result += "de l'";
			else if (entity.isMasculin()) result += "du ";
			else result += "de la ";
			break;
		case DEMONSTRATIF:
			if (pluriel) result += "ces ";
			else if (entity.isMasculin()) {
				if (VOYELLES.contains(entity.getLabel().substring(0, 1))) result += "cet ";
				else result += "ce ";
			}
			else result += "cette ";
			break;
		default:
			break;

		}
		
		result += pluriel? entity.getPluriel() :  entity.getLabel();
		if (withMaj) {
			result = result.substring(0, 1).toUpperCase() + result.substring(1);
		}
		
		return result;
		
	}

}
