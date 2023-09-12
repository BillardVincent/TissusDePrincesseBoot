package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PatronVersion extends AbstractEntity {

  @ManyToOne
  private Patron patron;

  private String nom;

  @OneToMany(mappedBy = "version", fetch = FetchType.LAZY)
  private List<TissuRequis> tissuRequis;

  @OneToMany(mappedBy = "version", fetch = FetchType.LAZY)
  private List<FournitureRequise> fournituresRequises;
  
}
