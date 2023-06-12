package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.logging.log4j.util.Strings;
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

	@OneToMany(mappedBy = "requis")
	@Cascade(CascadeType.PERSIST)
	private List<TissuRequisLaizeOption> option;

	@Enumerated(EnumType.STRING)
	private GammePoids gammePoids;

	@OneToMany(mappedBy = "requis")
    @Cascade(CascadeType.PERSIST)
	protected List<TissuVariant> variants;

	@Column(columnDefinition = "boolean default false")
	private boolean doublure;

	@Override
	public String toString() {
		return "tissu " + gammePoids + (doublure ? " (doublure)" : Strings.EMPTY);
	}
}
