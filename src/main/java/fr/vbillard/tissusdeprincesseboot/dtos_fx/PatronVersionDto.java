package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PatronVersionDto implements FxDto<PatronVersion> {
  private IntegerProperty id;
  private StringProperty name;

  public PatronVersionDto(){
    id = new SimpleIntegerProperty();
    name = new SimpleStringProperty();
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

  public String getName() {
    return name.get();
  }

  public StringProperty getIdName() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }
}
