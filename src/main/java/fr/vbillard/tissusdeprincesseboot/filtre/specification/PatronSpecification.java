package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class PatronSpecification implements Specification<Patron> {

	private static final long serialVersionUID = -6956433248687682190L;

	private CharacterSearch reference;
	private CharacterSearch marque;
	private CharacterSearch modele;
	private CharacterSearch typeVetement;
	private CharacterSearch description;
	private List<SupportTypeEnum> support;
	private Rangement rangement;

	// TissuRequis
	private NumericSearch<Integer> longueur;
	private NumericSearch<Integer> laize;
	private List<GammePoids> poids;

	// TissuVariant
	private List<Matiere> matieres;
	private List<Tissage> tissages;
	private List<TypeTissuEnum> typeTissu;

	private Boolean archived;

	private static class Joins {
		private Join<Patron, TissuRequis> joinTissuRequis;
		private Join<Patron, TissuRequisLaizeOption> joinTissuRequisLaizeOption;

		private Join<Patron, TissuRequis> joinTissuRequis(Root<Patron> root) {
			if (joinTissuRequis == null) {
				joinTissuRequis = root.join(Patron_.VERSIONS).join(PatronVersion_.TISSU_REQUIS);
			}
			return joinTissuRequis;
		}
		
		private Join<Patron, TissuRequisLaizeOption> joinTissuRequisLaizeOption(Root<Patron> root) {
			if (joinTissuRequisLaizeOption == null) {
				joinTissuRequisLaizeOption = root.join(Patron_.VERSIONS).join(PatronVersion_.TISSU_REQUIS).join(TissuRequis_.OPTION);
			}
			return joinTissuRequisLaizeOption;
		}
	}

	@Override
	public Predicate toPredicate(Root<Patron> patron, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Joins joins = new Joins();

		query.distinct(true);

		List<Predicate> predicateList = new ArrayList<>();

		if (reference != null) {
			predicateList
					.add(SpecificationUtils.getCharacterSearchPredicate(reference, patron.get(Patron_.REFERENCE), cb));
		}

		if (marque != null) {
			predicateList.add(SpecificationUtils.getCharacterSearchPredicate(marque, patron.get(Patron_.MARQUE), cb));
		}

		if (modele != null) {
			predicateList.add(SpecificationUtils.getCharacterSearchPredicate(modele, patron.get(Patron_.MODELE), cb));
		}

		if (description != null) {
			predicateList.add(
					SpecificationUtils.getCharacterSearchPredicate(description, patron.get(Patron_.DESCRIPTION), cb));
		}

		if (typeVetement != null) {
			predicateList.add(SpecificationUtils.getCharacterSearchPredicate(typeVetement,
					patron.get(Patron_.TYPE_VETEMENT), cb));
		}
		if (matieres != null) {
			predicateList.add(joins.joinTissuRequis(patron).get(TissuRequis_.MATIERES).in(matieres));
		}
		if (typeTissu != null) {
			predicateList.add(joins.joinTissuRequis(patron).get(TissuRequis_.TYPE_TISSU).in(typeTissu));
		}
		if (tissages != null) {
			predicateList.add(joins.joinTissuRequis(patron).get(TissuRequis_.TISSAGES).in(tissages));
		}
		if (longueur != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(longueur,
					joins.joinTissuRequisLaizeOption(patron).get(TissuRequisLaizeOption_.LONGUEUR), cb));
		}

		if (laize != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(laize,
					joins.joinTissuRequisLaizeOption(patron).get(TissuRequisLaizeOption_.LAIZE), cb));
		}

		if (!CollectionUtils.isEmpty(poids)) {
			predicateList.add(joins.joinTissuRequis(patron).get(TissuRequis_.GAMME_POIDS).in(poids));
		}
		
		if (!CollectionUtils.isEmpty(support)) {
			predicateList.add(patron.get(Patron_.SUPPORT_TYPE).in(support));
		}

		if(archived != null){
			predicateList.add(patron.get(Patron_.ARCHIVED).in(archived));
		}

		if (rangement != null){
			predicateList.add(patron.get(Patron_.RANGEMENT).in(rangement));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
