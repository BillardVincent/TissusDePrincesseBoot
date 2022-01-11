package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum UnitePoids {
	NON_RENSEIGNE("N/A"){
		@Override
	    public String toString() {
	      return "N/A";
	}},
	GRAMME_M("g/m"){
	    @Override
	    public String toString() {
	      return "g/m";
	    }},
	GRAMME_M_CARRE("g/m²"){
	    @Override
	    public String toString() {
	      return "g/m²";
	    }};
	    
    public final String label;

    UnitePoids(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
      }
    
    public static UnitePoids getEnum(String code){
        for(UnitePoids e : UnitePoids.values()){
            if(e.label.equals(code)) return e;
        }
        return null;
    }
    
    public static List<String> labels(){
    	List<String> list = new ArrayList();
        for(UnitePoids e : UnitePoids.values()){
            list.add(e.label);
        }
        return list;
    }
    
    public static String displayClassName() {
    	return "Unité de poids de tissu";
    }
}
