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
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture_;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Patron_;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Quantite_;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
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
	private NumericSearch<Float> quantiteDisponible;
	private NumericSearch<Float> quantiteSecondaire;

	private static class Joins {
		private Join<Fourniture, Quantite> joinQuantitePrimaire;
		private Join<Fourniture, Quantite> joinQuantiteSecondaire;

		private Join<Fourniture, Quantite> joinQuantitePrimaire(Root<Fourniture> root) {
			if (joinQuantitePrimaire == null) {
				joinQuantitePrimaire = root.join(Fourniture_.QUANTITE_PRINCIPALE);
			}
			return joinQuantitePrimaire;
		}

		private Join<Fourniture, Quantite> joinQuantiteSecondaire(Root<Fourniture> root) {
			if (joinQuantiteSecondaire == null) {
				joinQuantiteSecondaire = root.join(Fourniture_.QUANTITE_SECONDAIRE);
			}
			return joinQuantiteSecondaire;
		}
	}


	@Override
	public Predicate toPredicate(Root<Fourniture> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		query.distinct(true);
		FournitureSpecification.Joins joins = new FournitureSpecification.Joins();

		List<Predicate> predicateList = new ArrayList<>();

		if (type != null) {
			predicateList.add(root.get(Fourniture_.TYPE).in(type));
		}

		if (quantite != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantite,
					joins.joinQuantitePrimaire.get(Quantite_.QUANTITE), cb));
		}

		if (quantiteSecondaire != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantiteSecondaire,
					joins.joinQuantiteSecondaire.get(Quantite_.QUANTITE), cb));
		}

		if (quantiteDisponible != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantiteDisponible, root.get(Fourniture_.QUANTITE_DISPONIBLE), cb));
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
