package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;


@Getter
@Setter
@Entity
public class FournitureRequise extends AbstractRequis<Fourniture>{

  private String details;

  @ManyToOne
  private TypeFourniture type;

  protected float quantite;
  
  @Enumerated(EnumType.STRING)
  private Unite unite;

  private float quantiteSecMin;
  private float quantiteSecMax;

  @Enumerated(EnumType.STRING)
  private Unite uniteSecondaire;

}
