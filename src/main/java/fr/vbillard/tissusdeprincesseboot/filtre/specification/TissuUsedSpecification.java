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
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Patron_;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis_;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed_;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant_;
import fr.vbillard.tissusdeprincesseboot.model.Tissu_;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
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
public class TissuUsedSpecification implements Specification<TissuUsed> {

	private static final long serialVersionUID = -6956433248687682190L;

	private Projet projet;
	private TissuRequis tissuRequis;
	private NumericSearch<Integer> laize;
	private Boolean isDecati;

	private static class Joins {
		private Join<TissuUsed, Tissu> joinTissu;
		private Join<TissuUsed, Tissu> joinTissu(Root<TissuUsed> root) {
			if (joinTissu == null) {
				joinTissu = root.join(TissuUsed_.TISSU);
			}
			return joinTissu;
		}
	}

	@Override
	public Predicate toPredicate(Root<TissuUsed> tissuUsed, CriteriaQuery<?> query, CriteriaBuilder cb) {
		TissuUsedSpecification.Joins joins = new TissuUsedSpecification.Joins();

		query.distinct(true);

		List<Predicate> predicateList = new ArrayList<>();

		if (projet != null) {
			predicateList.add(tissuUsed.get(TissuUsed_.PROJET).in(projet));
		}

		if (tissuRequis != null) {
			predicateList.add(tissuUsed.get(TissuUsed_.REQUIS).in(tissuRequis));
		}

		if (laize != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(laize,
					joins.joinTissu(tissuUsed).get(Tissu_.LAIZE), cb));
		}

		if (isDecati != null) {
			predicateList.add(joins.joinTissu.get(Tissu_.DECATI).in(isDecati));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
