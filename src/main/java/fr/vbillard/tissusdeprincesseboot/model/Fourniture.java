package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Fourniture extends AbstractEntity{

  private String nom;
  private long quantite;
  private Unite unite;
  private String lieuAchat;
  private String reference;
  private String description;
  private long quantiteDisponible;

  @ManyToOne
  private TypeFourniture type;

  private boolean archived;



}
