package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class FournitureRequise extends AbstractEntity{

  private long quantite;
  private String details;

  @ManyToOne
  private Patron patron;

  @ManyToOne
  private TypeFourniture type;



	
}
