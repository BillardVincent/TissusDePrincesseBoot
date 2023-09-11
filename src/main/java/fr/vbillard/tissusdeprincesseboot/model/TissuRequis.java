package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequis extends AbstractRequis<Tissu> {

	@OneToMany(mappedBy = "requis")
	@Cascade({CascadeType.PERSIST, CascadeType.REMOVE})
	private List<TissuRequisLaizeOption> option;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = GammePoids.class)
	@CollectionTable(name = "TISSU_REQUIS_GAMME_POIDS",	joinColumns = @JoinColumn(name = "TISSU_REQUIS_ID"))
	private List<GammePoids> gammePoids;

	@Column(columnDefinition = "boolean default false")
	private boolean doublure;

	@ManyToMany
	private List<Matiere> matieres;

	@ManyToMany
	private List<Tissage> tissages;

	@Enumerated(EnumType.STRING)
	private TypeTissuEnum typeTissu;

	@Override
	public String toString() {
		return "tissu " + gammePoids + (doublure ? " (doublure)" : Strings.EMPTY);
	}
}
