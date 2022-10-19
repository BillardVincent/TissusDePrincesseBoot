package fr.vbillard.tissusdeprincesseboot.filtre.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;

public class FournitureSpecification implements Specification<Fourniture> {

  @Override
  public Predicate toPredicate(Root<Fourniture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return null;
  }
}
