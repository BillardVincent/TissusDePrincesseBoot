package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
