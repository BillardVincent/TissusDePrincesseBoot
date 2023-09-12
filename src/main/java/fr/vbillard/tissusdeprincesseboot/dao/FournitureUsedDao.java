package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournitureUsedDao extends Idao<FournitureUsed, Integer>{

	List<FournitureUsed> getAllByFourniture(Fourniture f);

	List<FournitureUsed> getAllByProjet(Projet p);

	List<FournitureUsed> getAllByRequisAndProjet(FournitureRequise fr, Projet p);

  boolean existsByFournitureId(int id);
}
