package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import fr.vbillard.tissusdeprincesseboot.filtre.searchObject.TissuSearch;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;

public class TissuSpecification implements Specification<Tissu> {
	private TissuSearch criteria;

	public TissuSpecification(TissuSearch ts) {
		criteria = ts;
	}

	@Override
	public Predicate toPredicate(Root<Tissu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		/*
		 * Join<Travel, Candidacy> o = root.join(Travel_.candidacy);
		 * 
		 * Path<Candidacy> candidacy = root.get(Travel_.candidacy); Path<Student>
		 * student = candidacy.get(Candidacy_.student); Path<String> lastName =
		 * student.get(Student_.lastName); Path<School> school =
		 * student.get(Student_.school);
		 * 
		 * Path<Period> period = candidacy.get(Candidacy_.period);
		 * Path<TravelStatusEnum> travelStatus = root.get(Travel_.travelStatus);
		 * Path<TravelTypeEnum> travelType = root.get(Travel_.travelType);
		 * 
		 * Path<Company> company = root.get(Travel_.company); Path<String> companyName =
		 * company.get(Company_.name);
		 * 
		 * final List<Predicate> predicates = new ArrayList<Predicate>();
		 * if(criteria.getSchool()!=null) { predicates.add(cb.equal(school,
		 * criteria.getSchool())); } if(criteria.getCompanyName()!=null) {
		 * predicates.add(cb.like(companyName, "%"+criteria.getCompanyName()+"%")); }
		 * if(criteria.getPeriod()!=null) { predicates.add(cb.equal(period,
		 * criteria.getPeriod())); } if(criteria.getTravelStatus()!=null) {
		 * predicates.add(cb.equal(travelStatus, criteria.getTravelStatus())); }
		 * if(criteria.getTravelType()!=null) { predicates.add(cb.equal(travelType,
		 * criteria.getTravelType())); } if(criteria.getLastName()!=null ) {
		 * predicates.add(cb.like(lastName, "%"+criteria.getLastName()+"%")); } return
		 * cb.and(predicates.toArray(new Predicate[predicates.size()]));
		 */

		return null;
	}
}
