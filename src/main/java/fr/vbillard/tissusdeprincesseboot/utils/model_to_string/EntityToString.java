package fr.vbillard.tissusdeprincesseboot.utils.model_to_string;

import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.Getter;

@Getter
public enum EntityToString {

	MATIERE(Matiere.class, "matière", null, false),
	PATRON(Patron.class, "patron", null, true),
	PROJET(Projet.class, "projet", null, true),
	TISSAGE(Tissage.class, "tissage", null, true),
	TISSU(Tissu.class, "tissu", null, true),
	OPTION_LAIZE(Tissu.class, "possibilité de longueur", "possibilités de longueur", false),
	TISSU_REQUIS(TissuRequis.class, "tissu requis", "tissus requis", true),
	TISSU_USED (TissuUsed.class, "tissu utilisé", "tissus utilisés", true),
	TYPE_TISSU(TypeTissuEnum.class, "type de tissu", "types de tissu", true),
	IMAGE(Photo.class, "image", null, false),
	FOURNITURE(Fourniture.class, "fourniture", null, false),
	FOURNITURE_REQUISE(FournitureRequise.class, "fourniture requise", "fournitures requises", false),
	FOURNITURE_USED(FournitureUsed.class, "fourniture utilisée", "fournitures utilisées", false),
	TYPE_FOURNITURE(TypeFourniture.class, "type de fourniture", "types de fourniture", true),
	PATRON_VERSION(PatronVersion.class, "version de patron" , "versions de patron", false),
	RANGEMENT(Rangement.class, "rangement" , null, true);

	private final String label;
	private final String pluriel;
	private final boolean isMasculin;
	private final Class<?> entity;
	
	EntityToString(Class<?> entity, String name, String pluriel, boolean isMasculin) {
		this.label = name;
		this.pluriel = pluriel == null ? name +"s" : pluriel;
		this.isMasculin = isMasculin;
		this.entity = entity;
	}
	
	public static EntityToString getByEntity(Class<?> clazz){
		for (EntityToString e : EntityToString.values()) {
			if (e.getEntity().equals(clazz)) {
				return e;
			}
		}
		throw new IllegalData();
	}
	
}
