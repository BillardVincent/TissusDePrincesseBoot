package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
  @OneToMany(mappedBy = "fournitureRequise")
  private List<FournitureVariant> fournitureVariants;



	
}
