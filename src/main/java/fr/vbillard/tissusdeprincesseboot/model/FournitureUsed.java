package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FournitureUsed extends AbstractUsedEntity<Fourniture> {

  protected float quantite;

  @ManyToOne
  private Fourniture fourniture;
  @ManyToOne
  private Projet projet;
  @ManyToOne
  private FournitureRequise requis;

  @Override
  public String toString() {
    return requis.getQuantite() + " de fourniture ref.\"" + fourniture.getReference() + "\" aloués au modèle "
        + projet.getPatronVersion().getPatron().getModele();
  }


}