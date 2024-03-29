package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Fourniture extends AbstractEntity{

  private String nom;
  private String lieuAchat;
  private String reference;
  private String description;
  private Float quantiteDisponible;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Quantite quantitePrincipale;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Quantite quantiteSecondaire;

  @ManyToOne
  private TypeFourniture type;
  @OneToOne
  @Cascade({org.hibernate.annotations.CascadeType.ALL})
  private ColorEntity color;

  @ManyToOne
  private Rangement rangement;

  @ColumnDefault("false")
  private boolean archived;
 
}
