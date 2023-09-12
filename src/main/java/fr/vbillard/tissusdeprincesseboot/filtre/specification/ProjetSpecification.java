package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Projet_;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class ProjetSpecification implements Specification<Projet> {

	private static final long serialVersionUID = -6956433248687682190L;

	private List<ProjectStatus> projectStatus;
	private Boolean archived;

	@Override
	public Predicate toPredicate(Root<Projet> projet, CriteriaQuery<?> query, CriteriaBuilder cb) {
		query.distinct(true);

		List<Predicate> predicateList = new ArrayList<>();

		if (projectStatus != null) {
			predicateList.add(projet.get(Projet_.STATUS).in(projectStatus));
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

}
