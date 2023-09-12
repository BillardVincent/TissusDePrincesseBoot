package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequisLaizeOption extends AbstractEntity{

  @Cascade(CascadeType.PERSIST)
  @ManyToOne
  private TissuRequis requis;

  private int longueur;

  private int laize;


}
