package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuUsed extends AbstractUsedEntity<Tissu> {

	protected int longueur;


	@ManyToOne
	protected Tissu tissu;

	@ManyToOne
	Projet projet;

	
	@Override
	public String toString() {
		return longueur + "cm de tissu ref.\""+tissu.getReference()+"\" aloués au modèle " + projet.getPatronVersion().getPatron().getModele();
	}

	@ManyToOne
	protected TissuRequis requis;
}
