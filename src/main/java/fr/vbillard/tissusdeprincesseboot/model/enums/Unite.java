package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;

public enum Unite {
  M("mètre", 100), CM("centimètre", 1), MM("milimètre",0.1f), UNITE("unité", 1), G("gramme", 1), KG("kilogramme", 1000)
  , L("litre", 1000), ML("millilitre", 1), M2 ("mètre carré", 1), NON_RENSEIGNE("N/A", 0);

  private String label;
  private float facteur;

  Unite(String label, float facteur) {
    this.label = label;
    this.facteur = facteur;
  }
  
  public String getLabel() {
	  return label;
  }
  public float getFacteur() {
	  return facteur;
  }
  
  public static Unite getEnum(String code){
      for(Unite e : Unite.values()){
          if(e.label.equals(code)) return e;
      }
      return null;
  }
  
  public static List<String> labels(){
  	List<String> list = new ArrayList<String>();
      for(Unite e : Unite.values()){
          list.add(e.label);
      }
      return list;
  }
  
  public static List<Unite> longueurUnite(){
	return Arrays.asList(M, CM, MM);
	  
  }

  public static List<Unite> volumeUnite(){
	return Arrays.asList(L, ML);
	  
  }
  
  public static List<Unite> poidUnite(){
	return Arrays.asList(G, KG);
	  
  }
  
  public float convertir(float value, Unite fromUnite, Unite toUnite) {
	  
	  if (poidUnite().containsAll(Arrays.asList(fromUnite, toUnite)) ||
        volumeUnite().containsAll(Arrays.asList(fromUnite, toUnite))||
        longueurUnite().containsAll(Arrays.asList(fromUnite, toUnite))) {
		  return value / fromUnite.getFacteur() * toUnite.getFacteur() ;
	  }
	  else {
		  throw new IllegalData("conversion impossible de "+fromUnite.label + " en " +toUnite.label);
	  }
	  
  }
  }
