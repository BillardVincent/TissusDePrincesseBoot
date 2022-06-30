package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Matiere_;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Projet_;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissage_;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed_;
import fr.vbillard.tissusdeprincesseboot.model.Tissu_;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class TissuSpecification implements Specification<Tissu> {

	private static final long serialVersionUID = -6956433248687682190L;

	public NumericSearch<Integer> longueur;
	public NumericSearch<Integer> laize;
	public List<Matiere> matieres;
	public List<Tissage> tissages;
	public CharacterSearch reference;
	public CharacterSearch description;
	public List<TypeTissuEnum> typeTissu;
	public Boolean chute;
	public NumericSearch<Integer> poids;
	public Boolean decati;
	public CharacterSearch lieuAchat;
	public Boolean archived;

	@Override
	public Predicate toPredicate(Root<Tissu> tissu, CriteriaQuery<?> query, CriteriaBuilder cb) {
		query.distinct(true);

		List<Predicate> predicateList = new ArrayList<>();

		if (matieres != null) {
			predicateList.add(tissu.get(Tissu_.MATIERE).in(matieres));
		}
		if (typeTissu != null) {
			predicateList.add(tissu.get(Tissu_.TYPE_TISSU).in(typeTissu));
		}
		if (tissages != null) {
			predicateList.add(tissu.get(Tissu_.TISSAGE).in(tissages));
		}

		if (longueur != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(longueur, tissu.get(Tissu_.LONGUEUR), cb));
		}

		if (laize != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(laize, tissu.get(Tissu_.LAIZE), cb));
		}

		if (poids != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(poids, tissu.get(Tissu_.POIDS), cb));
		}

		if (reference != null) {
			predicateList
					.add(SpecificationUtils.getCharacterSearchPredicate(reference, tissu.get(Tissu_.REFERENCE), cb));
		}

		if (description != null) {
			predicateList.add(
					SpecificationUtils.getCharacterSearchPredicate(description, tissu.get(Tissu_.DESCRIPTION), cb));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
