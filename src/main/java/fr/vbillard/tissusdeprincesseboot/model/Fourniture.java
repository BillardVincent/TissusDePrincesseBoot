package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Fourniture extends AbstractEntity{

  private String nom;
  private float quantite;
  private String lieuAchat;
  private String reference;
  private String description;
  private float quantiteDisponible;
  private Unite dimension;
  private float quantiteSecondaire;
  private Unite dimensionSecondaire;
  @ManyToOne
  private TypeFourniture type;

  private boolean archived;



}
