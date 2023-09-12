package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PatronVersionDto implements FxDto<PatronVersion> {
  private IntegerProperty id;
  private StringProperty nom;

  public PatronVersionDto(){
    id = new SimpleIntegerProperty();
    nom = new SimpleStringProperty();
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

  public String getNom() {
    return nom.get();
  }

  public StringProperty getNomProperty() {
    return nom;
  }

  public void setNom(String name) {
    this.nom.set(name);
  }
}
