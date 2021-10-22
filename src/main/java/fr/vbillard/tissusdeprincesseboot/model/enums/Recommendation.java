package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum Recommendation {
	IDEAL("idéal"){
		@Override
	    public String toString() {
	      return "idéal";
	}},
	POSSIBLE("possible"){
	    @Override
	    public String toString() {
	      return "possible";
	    }},
	NON_RECOMMANDE("non recommandé"){
		    @Override
		    public String toString() {
		      return "non recommandé";
		    }},
	INCONNU("?"){
	    @Override
	    public String toString() {
	      return "?";
	    }};
	    
    public final String label;

    private Recommendation(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
      }
    
    public static Recommendation getEnum(String code){
        for(Recommendation e : Recommendation.values()){
            if(e.label.equals(code)) return e;
        }
        return null;
    }
    
    public static List<String> labels(){
    	List<String> list = new ArrayList();
        for(Recommendation e : Recommendation.values()){
            list.add(e.label);
        }
        return list;
    }
    
    public static String displayClassName() {
    	return "Recommendation";
    }
}
