package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
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
public class TissuSpecification implements Specification<Tissu> {

	private static final long serialVersionUID = -6956433248687682190L;

	public NumericSearch<Integer> longueur;
	public NumericSearch<Integer> laize;
	public List<Matiere> matieres;
	public List<Tissage> tissages;
	public CharacterSearch reference;
	public String description;
	public List<TypeTissuEnum> typeTissu;
	public Boolean chute;
	public NumericSearch<Integer> poids;
	public UnitePoids unitePoids;
	public Boolean decati;
	public CharacterSearch lieuAchat;
	public Boolean archived;

	private static class Joins {
		private Join<Tissu, Matiere> joinMatiere;
		private Join<Tissu, Tissage> joinTissage;
		private Join<Tissu, TissuUsed> joinTissuUsed;
		private Join<TissuUsed, Projet> joinProjet;

		private Join<Tissu, Matiere> joinMatiere(Root<Tissu> root) {
			if (joinMatiere == null) {
				joinMatiere = root.join(Tissu_.MATIERE);
			}
			return joinMatiere;
		}

		private Join<Tissu, Tissage> joinTissage(Root<Tissu> root) {
			if (joinTissage == null) {
				joinTissage = root.join(Tissu_.TISSAGE);
			}
			return joinTissage;
		}

		private Join<Tissu, TissuUsed> joinTissuUsed(Root<TissuUsed> root) {
			if (joinTissuUsed == null) {
				joinTissuUsed = root.join(TissuUsed_.TISSU);
			}
			return joinTissuUsed;
		}

		private Join<TissuUsed, Projet> joinProjet(Root<TissuUsed> root) {
			if (joinProjet == null) {
				joinProjet = root.join(TissuUsed_.TISSU).join(TissuUsed_.PROJET);
			}
			return joinProjet;
		}

	}

	@Override
	public Predicate toPredicate(Root<Tissu> tissu, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Joins joins = new Joins();
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

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

	/*
	 * query.distinct(true); List<Predicate> predicateList = new ArrayList<>();
	 * 
	 * if (userUuid != null) {
	 * predicateList.add(cb.equal(root.get(Contact_.neoUserUuid), userUuid)); }
	 * 
	 * if (familyName != null) {
	 * predicateList.add(SpecificationUtils.getCharacterSearchPredicate(familyName,
	 * root.get(Contact_.familyName), cb)); } if (givenName != null) {
	 * predicateList.add(SpecificationUtils.getCharacterSearchPredicate(givenName,
	 * root.get(Contact_.givenName), cb)); } if (jobTitle != null) {
	 * predicateList.add(SpecificationUtils.getCharacterSearchPredicate(jobTitle,
	 * root.get(Contact_.jobTitle), cb)); } if(channelsFormat == null) {
	 * predicateList.add(cb.or( searchChannelWithType(root, cb,
	 * Contact_.contactEmails), searchChannelWithType(root, cb,
	 * Contact_.contactIbans), searchChannelWithType(root, cb,
	 * Contact_.contactPhones) )); } else { switch (channelsFormat) { case EMAIL:
	 * predicateList.add(searchChannelWithType(root, cb, Contact_.contactEmails));
	 * break; case IBAN: predicateList.add(searchChannelWithType(root, cb,
	 * Contact_.contactIbans)); break; case PHONE:
	 * predicateList.add(searchChannelWithType(root, cb, Contact_.contactPhones));
	 * break; } }
	 * 
	 * if (!CollectionUtils.isEmpty(userUuidList)) {
	 * predicateList.add(root.get(Contact_.neoUserUuid).in(userUuidList)); }
	 * 
	 * return cb.and(predicateList.toArray(Predicate[]::new));
	 */
}
