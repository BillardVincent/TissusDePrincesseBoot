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
public class TissuUsed extends AbstractUsedEntity<Tissu> {

	protected int longueur;

	@ManyToOne
	Projet projet;

	
	@Override
	public String toString() {
		return requis.getQuantite() + "cm de tissu ref.\""+tissu.getReference()+"\" aloués au modèle " + projet.getPatron().getModele();	
	}

	@ManyToOne
	protected TissuRequis requis;
}
