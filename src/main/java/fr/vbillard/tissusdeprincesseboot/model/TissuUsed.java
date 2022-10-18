package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuUsed extends AbstractEntity implements FxDto{

	protected int longueur;

	@ManyToOne
	Tissu tissu;
	@ManyToOne
	Projet projet;
	@ManyToOne
	TissuRequis tissuRequis;
	
	@Override
	public String toString() {
		return tissuRequis.getLongueur() + "cm de tissu ref.\""+tissu.getReference()+"\" aloués au modèle " + projet.getPatron().getModele();	
	}


}
