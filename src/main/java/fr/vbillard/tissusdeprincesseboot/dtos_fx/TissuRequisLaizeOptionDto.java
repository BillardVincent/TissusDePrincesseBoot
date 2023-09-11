package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class TissuRequisLaizeOptionDto implements FxDto<TissuRequisLaizeOption> {

  private IntegerProperty id;

  private IntegerProperty longueur;

  private IntegerProperty laize;

  public TissuRequisLaizeOptionDto(){
    this.id = new SimpleIntegerProperty();
    this.longueur = new SimpleIntegerProperty();
    this.laize = new SimpleIntegerProperty();
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty getIdProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public int getLongueur() {
    return longueur.get();
  }

  public IntegerProperty getLongueurProperty() {
    return longueur;
  }

  public void setLongueur(int longueur) {
    this.longueur.set(longueur);
  }

  public int getLaize() {
    return laize.get();
  }

  public IntegerProperty getLaizeProperty() {
    return laize;
  }

  public void setLaize(int laize) {
    this.laize.set(laize);
  }
}
