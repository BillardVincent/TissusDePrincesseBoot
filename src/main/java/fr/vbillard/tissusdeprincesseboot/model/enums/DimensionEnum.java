package fr.vbillard.tissusdeprincesseboot.model.enums;

public enum DimensionEnum {
  LONGUEUR("longueur", new Unite[]{Unite.CM, Unite.MM, Unite.M, Unite.NON_RENSEIGNE}),
  POIDS("poids", new Unite[]{Unite.G, Unite.KG, Unite.NON_RENSEIGNE}),
  SURFACE("surface", new Unite[]{Unite.M2, Unite.NON_RENSEIGNE}),
  NON_RENSEIGNE("N/A", new Unite[]{Unite.NON_RENSEIGNE}),
  VOLUME("volume", new Unite[]{Unite.L, Unite.ML, Unite.NON_RENSEIGNE});

  private String label;
  private Unite[] unites;

  DimensionEnum(String label, Unite[] unites) {
    this.label = label;
    this.unites = unites;
  }

  public String getLabel() {
    return label;
  }

  public Unite[] getUnites() {
    return unites;
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
