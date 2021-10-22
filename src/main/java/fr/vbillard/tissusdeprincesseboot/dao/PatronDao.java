package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Patron;

@Repository
public interface PatronDao extends JpaRepository<Patron, Integer> {

	 boolean existsByReference(String ref);

}
