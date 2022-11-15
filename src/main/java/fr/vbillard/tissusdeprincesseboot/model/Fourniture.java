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
  private Float quantite;
  private String lieuAchat;
  private String reference;
  private String description;
  private Float quantiteDisponible;
  private Unite dimension;
  private Float quantiteSecondaire;
  private Unite dimensionSecondaire;
  @ManyToOne
  private TypeFourniture type;

  private boolean archived;



}
