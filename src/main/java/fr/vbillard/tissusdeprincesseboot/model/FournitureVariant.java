package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FournitureVariant extends AbstractVariant<Fourniture> {

  protected long quantite;

  @ManyToOne
  private FournitureRequise requis;
  @ManyToOne
  private TypeFourniture typeFourniture;
  @Enumerated(EnumType.STRING)
  private Unite uniteSecondaire;
  private float quantiteSecMin;
  private float quantiteSecMax;


  @Override
  public String toString() {
    return "fourniture : " + requis + ", type : " + typeFourniture ;
  }


}