package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;

public interface TissuDao extends JpaRepository<Tissu, Integer> {
	
	boolean existsByReference(String reference);
	

}
