package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quantite extends AbstractEntity{
  private Unite unite;
  private Float quantite;
}
