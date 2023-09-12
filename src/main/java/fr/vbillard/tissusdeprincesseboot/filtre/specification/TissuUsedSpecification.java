package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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
			predicateList.add(joins.joinTissu(tissuUsed).get(Tissu_.DECATI).in(isDecati));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
