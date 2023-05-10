package fr.vbillard.tissusdeprincesseboot.model;

import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;


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

  @Override
  public List<? extends AbstractVariant<Fourniture>> getVariants() {
    return Collections.emptyList();
  }
}
