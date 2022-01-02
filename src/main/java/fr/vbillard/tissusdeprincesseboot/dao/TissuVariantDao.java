package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;

@Repository
public interface TissuVariantDao extends JpaRepository<TissuVariant, Integer> {
	
	List<TissuVariant> getAllByTissuRequisId(int id);
	
}
