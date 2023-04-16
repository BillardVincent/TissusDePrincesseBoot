package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;

public enum Unite {
	M("mètre", "m", 100), CM("centimètre", "cm", 1), MM("milimètre", "mm", 0.1f), UNITE("unité", "", 1), G("gramme", "g", 1),
	KG("kilogramme", "kg", 1000), L("litre", "l", 1000), ML("millilitre",
			"ml", 1), M2("mètre carré", "m²", 1), NON_RENSEIGNE("N/A",	"N/A",0);

	private final String label;
	private final String abbreviation;
	private final float facteur;

	Unite(String label, String abbreviation, float facteur) {
		this.label = label;
		this.abbreviation = abbreviation;
		this.facteur = facteur;
	}

	public String getLabel() {
		return label;
	}

	public String getAbbreviation(){return abbreviation;}

	public float getFacteur() {
		return facteur;
	}

	public static Unite getEnum(String code) {
		for (Unite e : Unite.values()) {
			if (e.label.equals(code))
				return e;
		}
		return null;
	}

	public static List<String> labels() {
		List<String> list = new ArrayList<>();
		for (Unite e : Unite.values()) {
			list.add(e.label);
		}
		return list;
	}

	public static List<String> getValuesByDimension(DimensionEnum dimension) {
		List<String> list = new ArrayList<>();
		if (dimension == null) {
			list.add(Unite.NON_RENSEIGNE.label);
		} else {
			for (Unite e : dimension.getUnites()) {
				list.add(e.label);
			}
		}
		return list;
	}

	public static List<Unite> longueurUnite() {
		return Arrays.asList(M, CM, MM);

	}

	public static List<Unite> volumeUnite() {
		return Arrays.asList(L, ML);

	}

	public static List<Unite> poidUnite() {
		return Arrays.asList(G, KG);
	}

	public float convertir(float value, Unite fromUnite, Unite toUnite) {

		if (fromUnite == toUnite){
			return value;
		}
		List<Unite> lst = Arrays.asList(fromUnite, toUnite);
		if (DimensionEnum.LONGUEUR.getUnites().containsAll(lst)
				|| DimensionEnum.POIDS.getUnites().containsAll(lst)
				|| DimensionEnum.VOLUME.getUnites().containsAll(lst)) {
			return value / fromUnite.getFacteur() * toUnite.getFacteur();
		}

		throw new IllegalData("conversion impossible de " + fromUnite.label + " en " + toUnite.label);

	}

	public static float convertir(float value, Unite fromUnite) {
			return value / fromUnite.getFacteur();
	}
}
