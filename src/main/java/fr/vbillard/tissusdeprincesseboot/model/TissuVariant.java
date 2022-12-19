package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuVariant extends AbstractVariant<Tissu> {

	@Override
	public String toString() {
		return "matiere : " + matiere + ", type : " + typeTissu + ", tissage : " + tissage;
	}


	@Enumerated(EnumType.STRING)
	private TypeTissuEnum typeTissu;
	@ManyToOne
	private Matiere matiere;
	@ManyToOne
	private Tissage tissage;
	
	@ManyToOne
	protected TissuRequis requis;

}
