package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequis extends AbstractEntity {

	@ManyToOne
	private Patron patron;
	private int longueur;
	private int laize;
	@Enumerated(EnumType.STRING)
	private GammePoids gammePoids;
	@OneToMany(mappedBy = "tissuRequis")
	private List<TissuVariant> tissuVariants;

	// private Patron patron;

}
