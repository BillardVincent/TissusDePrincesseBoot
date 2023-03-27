package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequis extends AbstractRequis<Tissu> {


	private int laize;
	@Enumerated(EnumType.STRING)
	private GammePoids gammePoids;

	@ManyToOne
    @Cascade(CascadeType.PERSIST)
	private Patron patron;
	
	@OneToMany(mappedBy = "requis")
    @Cascade(CascadeType.PERSIST)
	protected List<TissuVariant> variants;

	private int longueur;

	private boolean doublure;

}
