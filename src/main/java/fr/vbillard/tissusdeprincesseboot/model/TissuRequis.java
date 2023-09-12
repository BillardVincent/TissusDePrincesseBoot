package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

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
