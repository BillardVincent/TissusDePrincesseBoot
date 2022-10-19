package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FournitureUsed extends AbstractEntity implements FxDto {

  protected long quantite;

  @ManyToOne
  private Fourniture fourniture;
  @ManyToOne
  private Projet projet;
  @ManyToOne
  private FournitureRequise founitureRequise;

  @Override
  public String toString() {
    return founitureRequise.getQuantite() + " de fourniture ref.\"" + fourniture.getReference() + "\" aloués au modèle "
        + projet.getPatron().getModele();
  }
}