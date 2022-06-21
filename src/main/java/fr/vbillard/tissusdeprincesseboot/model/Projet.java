package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Projet extends AbstractEntity {

	private String description;

	@ManyToOne
	private Patron patron;

	@Enumerated(EnumType.STRING)
	private ProjectStatus status;

	/*
	 * @OneToMany private TissuUsed tissuUsed;
	 * 
	 * private Map<FounitureRequise, List<FournitureUsed>> fournitureUsed;
	 * 
	 * Projet(Patron patron){ tissuUsed = new HashMap<TissuRequis,
	 * List<TissuUsed>>(); for(TissuRequis tr : patron.getTissusRequis()) {
	 * tissuUsed.put(tr, new ArrayList()); } fournitureUsed = new
	 * HashMap<FounitureRequise, List<FournitureUsed>>(); for(FounitureRequise fr :
	 * patron.getFournituresRequises()) { fournitureUsed.put(fr, new ArrayList()); }
	 * 
	 * }
	 */
}
