package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TissuRequisDto extends AbstractRequisDto<TissuRequis, Tissu> {

  private IntegerProperty id;
  private StringProperty gammePoids;
  private IntegerProperty patronId;
  private BooleanProperty doublure;

  public TissuRequisDto() {
    id = new SimpleIntegerProperty(0);
    gammePoids = new SimpleStringProperty("");
    patronId = new SimpleIntegerProperty(0);
    variants = new SimpleListProperty<>();
    doublure = new SimpleBooleanProperty();
  }

  @Override
  public String toString() {
    return "Tissu " + getGammePoids();
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

  public void setGammePoids(GammePoids gammePoids2) {
    this.gammePoids.set(gammePoids2 == null ? GammePoids.NON_RENSEIGNE.label : gammePoids2.label);
  }

  public void setGammePoids(String label) {
    this.gammePoids.set(label);
  }

  public String getGammePoids() {
    return gammePoids.get();
  }

  public StringProperty getGammePoidsProperty() {
    return gammePoids;
  }

  public int getPatronId() {
    return patronId.get();
  }

  public IntegerProperty getPatronIdProperty() {
    return patronId;
  }

  public void setPatronId(int patronId) {
    this.patronId.set(patronId);
  }

  public boolean isDoublure() {
    return doublure.get();
  }

  public BooleanProperty doublureProperty() {
    return doublure;
  }

  public void setDoublure(boolean doublure) {
    this.doublure.set(doublure);
  }
}
