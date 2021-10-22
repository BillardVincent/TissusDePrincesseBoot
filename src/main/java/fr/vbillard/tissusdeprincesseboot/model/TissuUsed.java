package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuUsed extends AbstractEntity {

	protected int longueur;

	@ManyToOne
	Tissu tissu;
	@ManyToOne
	Projet projet;
	@ManyToOne
	TissuRequis tissuRequis;


}
