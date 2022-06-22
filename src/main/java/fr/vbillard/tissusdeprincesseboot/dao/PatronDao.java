package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Patron;

@Repository
public interface PatronDao extends Idao<Patron, Integer> {

	boolean existsByReference(String ref);

}
