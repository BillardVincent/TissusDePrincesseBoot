package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.SpecificationUtils;
import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
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
public class TissuSpecification implements Specification<Tissu> {

	private static final long serialVersionUID = -6956433248687682190L;

	private NumericSearch<Integer> longueur;
	private NumericSearch<Integer> laize;
	private List<Matiere> matieres;
	private List<Tissage> tissages;
	private CharacterSearch reference;
	private CharacterSearch description;
	private List<TypeTissuEnum> typeTissu;
	private Boolean chute;
	private NumericSearch<Integer> poids;
	private Boolean poidsNC;
	private Boolean decati;
	private CharacterSearch lieuAchat;
	private Boolean archived;
	private RGBColor color;
	private Rangement rangement;


	private static class Joins {
		private Join<Tissu, ColorEntity> joinColor;
		private Join<Tissu, ColorEntity> joinColor(Root<Tissu> root) {
			if (joinColor == null) {
				joinColor = root.join(Tissu_.COLOR);
			}
			return joinColor;
		}
	}

	@Override
	public Predicate toPredicate(Root<Tissu> tissu, CriteriaQuery<?> query, CriteriaBuilder cb) {
		TissuSpecification.Joins joins = new TissuSpecification.Joins();
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

		if (poidsNC != null && poidsNC) {
			if (poids != null) {
				Predicate nc = SpecificationUtils.getNumericSearchPredicate(new NumericSearch<>(0),
						tissu.get(Tissu_.POIDS), cb);
				Predicate value = SpecificationUtils.getNumericSearchPredicate(poids, tissu.get(Tissu_.POIDS), cb);

				Predicate or = cb.or(nc, value);
				predicateList.add(or);
			} else {
				predicateList.add(SpecificationUtils.getNumericSearchPredicate(new NumericSearch<>(0),
						tissu.get(Tissu_.POIDS), cb));
			}
		} else if (poids != null) {
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
		
		if (lieuAchat != null) {
			predicateList.add(
					SpecificationUtils.getCharacterSearchPredicate(description, tissu.get(Tissu_.LIEU_ACHAT), cb));
		}

		if(archived != null){
			predicateList.add(tissu.get(Tissu_.ARCHIVED).in(archived));
		}

		if (color != null) {
			predicateList.add(SpecificationUtils.getColorPredicate(ColorUtils.rgbToLab(color),
					new Path[]{joins.joinColor(tissu).get(ColorEntity_.L),
							joins.joinColor(tissu).get(ColorEntity_.A),
							joins.joinColor(tissu).get(ColorEntity_.B)}, cb));
		}

		if (rangement != null){
			predicateList.add(tissu.get(Tissu_.RANGEMENT).in(rangement));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
