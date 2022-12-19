package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;

@Repository
public interface TissuUsedDao extends Idao<TissuUsed, Integer> {

	List<TissuUsed> getAllByTissu(Tissu tissu);

	List<TissuUsed> getAllByRequisAndProjet(TissuRequis tissuRequis, Projet projet);

	List<TissuUsed> getAllByProjet(Projet projet);

	boolean existsByTissuId(int id);

}
