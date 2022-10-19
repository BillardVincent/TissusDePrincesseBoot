package fr.vbillard.tissusdeprincesseboot.model.enums;

public enum Unite {
  M("mètre", 1000), CM("centimètre", 10), MM("milimètre",1), UNITE("unité", 1), G("gramme", 1), KG("kilogramme", 1000)
  , L("litre", 1000), ML("millilitre", 1), M2 ("mètre carré", 1);

  private String label;
  private int facteur;

  Unite(String label, int facteur) {
    this.label = label;
    this.facteur = facteur;
  }

  }
