package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import fr.vbillard.tissusdeprincesseboot.utils.color.RGBColor;
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
	private RGBColor color;

	private static class Joins {
		private Join<Fourniture, Quantite> joinQuantitePrimaire;
		private Join<Fourniture, Quantite> joinQuantiteSecondaire;
		private Join<Tissu, ColorEntity> joinColor;


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

		private Join<Tissu, ColorEntity> joinColor(Root<Fourniture> root) {
			if (joinColor == null) {
				joinColor = root.join(Fourniture_.COLOR);
			}
			return joinColor;
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
					joins.joinQuantitePrimaire(root).get(Quantite_.QUANTITE), cb));
		}

		if (quantiteSecondaire != null) {
			predicateList.add(SpecificationUtils.getNumericSearchPredicate(quantiteSecondaire,
					joins.joinQuantiteSecondaire(root).get(Quantite_.QUANTITE), cb));
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

		if (color != null) {
			predicateList.add(SpecificationUtils.getColorPredicate(ColorUtils.rgbToLab(color),
					new Path[]{joins.joinColor(root).get(ColorEntity_.L),
							joins.joinColor(root).get(ColorEntity_.A),
							joins.joinColor(root).get(ColorEntity_.B)}, cb));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}
}
