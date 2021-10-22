package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;

@Repository
public interface TissuUsedDao extends JpaRepository<TissuUsed, Integer> {

	 List<TissuUsed> getAllByTissu(Tissu tissu);
	 List<TissuUsed> getAllByTissuRequisAndProjet(TissuRequis tissuRequis, Projet projet);

}
