package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import java.util.List;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class TissuRequisDto extends AbstractRequisDto<TissuRequis, Tissu> {

  private IntegerProperty id;
  private ListProperty<GammePoids> gammePoids;
  private TypeTissuEnum typeTissus;
  private ListProperty<String> matieres;
  private ListProperty<String> tissages;
  private IntegerProperty versionId;
  private BooleanProperty doublure;

  public TissuRequisDto() {
    id = new SimpleIntegerProperty(0);
    gammePoids = new SimpleListProperty<>();
    versionId = new SimpleIntegerProperty(0);
    doublure = new SimpleBooleanProperty();
    matieres = new SimpleListProperty<>();
    tissages = new SimpleListProperty<>();
  }

  @Override
  public String toString() {
    return "Tissu Requis";
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

  public void setGammePoids(List<GammePoids> gammePoids) {
    this.gammePoids.set(FXCollections.observableList(gammePoids));
  }

  public List<GammePoids> getGammePoids() {
    return gammePoids.get();
  }

  public ListProperty<GammePoids> getGammePoidsProperty() {
    return gammePoids;
  }

  public void setTypeTissu(TypeTissuEnum typeTissu) {
    this.typeTissus = typeTissu;
  }

  public TypeTissuEnum getTypeTissu() {
    return typeTissus;
  }

  public void setTissage(List<String> tissages) {
    this.tissages.set(FXCollections.observableList(tissages));
  }

  public List<String> getTissage() {
    return tissages.get();
  }

  public ListProperty<String> getTissageProperty() {
    return tissages;
  }

  public void setMatiere(List<String> matiere) {
    this.matieres.set(FXCollections.observableList(matiere));
  }

  public List<String> getMatiere() {
    return matieres.get();
  }

  public ListProperty<String> getMatiereProperty() {
    return matieres;
  }

  public int getVersionId() {
    return versionId.get();
  }

  public IntegerProperty getVersionIdProperty() {
    return versionId;
  }

  public void setVersionId(int versionId) {
    this.versionId.set(versionId);
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
