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
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Patron_;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class PatronSpecification implements Specification<Patron> {

	private static final long serialVersionUID = -6956433248687682190L;

	private CharacterSearch reference;
	private CharacterSearch marque;
	private CharacterSearch modele;
	private CharacterSearch typeVetement;
	private CharacterSearch description;
	public Boolean archived;

	@Override
	public Predicate toPredicate(Root<Patron> patron, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
