package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DimensionEnum {
  LONGUEUR("longueur", Arrays.asList(Unite.CM, Unite.MM, Unite.M, Unite.NON_RENSEIGNE)),
  POIDS("poids", Arrays.asList(Unite.G, Unite.KG, Unite.NON_RENSEIGNE)),
  SURFACE("surface",Arrays.asList(Unite.M2, Unite.NON_RENSEIGNE)),
  NON_RENSEIGNE("N/A", Arrays.asList(Unite.NON_RENSEIGNE)),
  NOMBRE("nombre", Arrays.asList(Unite.UNITE)),
  VOLUME("volume", Arrays.asList(Unite.L, Unite.ML, Unite.NON_RENSEIGNE));

  private String label;
  private List<Unite> unites;

  DimensionEnum(String label, List<Unite> unites) {
    this.label = label;
    this.unites = unites;
  }

  public String getLabel() {
    return label;
  }

  public List<Unite> getUnites() {
    return unites;
  }
  
  public static List<String> labels(){
  	List<String> list = new ArrayList<>();
      for(DimensionEnum e : DimensionEnum.values()){
          list.add(e.label);
      }
      return list;
  }

  public Unite getDefault(){
    for(Unite e : getUnites()){
      if(e.getFacteur() == 1) return e;
    }
    return Unite.NON_RENSEIGNE;
  }

  public static DimensionEnum getEnum(String code){
    for(DimensionEnum e : DimensionEnum.values()){
      if(e.label.equals(code)) return e;
    }
    return null;
  }
  }
