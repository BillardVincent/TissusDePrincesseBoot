package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum SupportTypeEnum {
	
	NON_RENSEIGNE ("non renseigné"),
	PDF ("pdf"),
	PAPIER("papier"),
	PROJECTION ("projection");

	public final String label;

	SupportTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static SupportTypeEnum getEnum(String code) {
		for (SupportTypeEnum e : SupportTypeEnum.values()) {
			if (e.label.equals(code))
				return e;
		}
		return null;
	}

	public static List<String> labels() {
		List<String> list = new ArrayList();
		for (SupportTypeEnum e : SupportTypeEnum.values()) {
			list.add(e.label);
		}
		return list;
	}
	
}
