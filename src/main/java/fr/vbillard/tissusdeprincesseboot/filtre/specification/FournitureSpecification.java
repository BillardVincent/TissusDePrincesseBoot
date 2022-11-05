package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture_;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
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
public class FournitureSpecification implements Specification<Fourniture> {

	private static final long serialVersionUID = 1L;

	private CharacterSearch reference;
	private CharacterSearch description;
	private List<TypeFourniture> type;
	private CharacterSearch lieuAchat;
	private Boolean archived;
	private String nom;
	private NumericSearch<Float> quantite;
	private Unite unite;
	private NumericSearch<Float> quantiteDisponible;

	@Override
	public Predicate toPredicate(Root<Fourniture> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		query.distinct(true);

		List<Predicate> predicateList = new ArrayList<>();

		if (type != null) {
			predicateList.add(root.get(Fourniture_.TYPE).in(type));
		}

		if (quantite != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantite, root.get(Fourniture_.QUANTITE), cb));
		}

		if (quantiteDisponible != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantiteDisponible, root.get(Fourniture_.QUANTITE_DISPONIBLE), cb));
		}

		if (unite != null) {
			predicateList.add(root.get(Fourniture_.UNITE).in(unite));
		}

		if (reference != null) {
			predicateList
					.add(SpecificationUtils.getCharacterSearchPredicate(reference, root.get(Fourniture_.REFERENCE), cb));
		}
		
		if (nom != null) {
			predicateList
					.add(SpecificationUtils.getCharacterSearchPredicate(reference, root.get(Fourniture_.NOM), cb));
		}


		if (description != null) {
			predicateList.add(
					SpecificationUtils.getCharacterSearchPredicate(description, root.get(Fourniture_.DESCRIPTION), cb));
		}
		
		if (lieuAchat != null) {
			predicateList.add(
					SpecificationUtils.getCharacterSearchPredicate(description, root.get(Fourniture_.LIEU_ACHAT), cb));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}
}
