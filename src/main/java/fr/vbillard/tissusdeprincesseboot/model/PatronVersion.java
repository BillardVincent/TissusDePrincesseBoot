package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PatronVersion extends AbstractEntity {

  @ManyToOne
  private Patron patron;

  private String name;

  @Cascade(CascadeType.PERSIST)
  @OneToMany(mappedBy = "version", fetch = FetchType.LAZY)
  private List<TissuRequis> tissuRequis;

  @Cascade(CascadeType.PERSIST)
  @OneToMany(mappedBy = "version", fetch = FetchType.LAZY)
  private List<FournitureRequise> fournituresRequises;
}
