package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequis extends AbstractEntity{
	

	@ManyToOne
	private Patron patron;
	private int longueur;
	private int laize;
	@Enumerated(EnumType.STRING)
	private GammePoids gammePoids;

	
	

	//private Patron patron;
	
	
	
}
