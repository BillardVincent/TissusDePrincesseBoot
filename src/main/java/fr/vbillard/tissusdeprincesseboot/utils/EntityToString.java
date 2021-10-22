package fr.vbillard.tissusdeprincesseboot.utils;

import lombok.Getter;

@Getter
public enum EntityToString {

	MATIERE("matière", null, false),
	PATRON("patron", null, true),
	PROJET("projet", null, true),
	TISSAGE("tissage", null, true),
	TISSU("tissu", null, true),
	TISSU_REQUIS("tissu requis", "tissus requis", true),
	TISSU_USED ("tissu utilisé", "tissus utilisés", true),
	TYPE_TISSU("type de tissu", "types de tissu", true),
	IMAGE("image", null, false);

	
	private String label;
	private String pluriel;
	private boolean isMasculin;
	
	EntityToString(String name, String pluriel, boolean isMasculin) {
		this.label = name;
		this.pluriel = pluriel == null ? name +"s" : pluriel;
		this.isMasculin = isMasculin;
	}
	
}
