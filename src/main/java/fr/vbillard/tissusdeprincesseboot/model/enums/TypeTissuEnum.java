package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum TypeTissuEnum {

	CHAINE_ET_TRAME("chaîne et trame"), MAILLE("maille"), NON_TISSE("non tissé"), MIXILIGNE("tulle ou dentelle"),
	NON_RENSEIGNE("non renseigné");

	TypeTissuEnum(String label) {
		this.label = label;
	}

	public final String label;

	public String getLabel() {
		return this.label;
	}

	public static TypeTissuEnum getEnum(String code) {
		for (TypeTissuEnum e : TypeTissuEnum.values()) {
			if (e.label.equals(code))
				return e;
		}
		return null;
	}

	public static List<String> labels() {
		List<String> list = new ArrayList<>();
		for (TypeTissuEnum e : TypeTissuEnum.values()) {
			list.add(e.label);
		}
		return list;
	}

	public static String displayClassName() {
		return "Type";
	}

}
