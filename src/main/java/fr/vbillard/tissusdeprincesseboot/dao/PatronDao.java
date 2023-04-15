package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Patron;

@Repository
public interface PatronDao extends Idao<Patron, Integer> {

	boolean existsByReference(String ref);

	Page<Patron> getAllByArchived(Pageable pageable, boolean archived);
}
