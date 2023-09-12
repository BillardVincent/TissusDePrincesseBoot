package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournitureRequiseDao extends Idao<FournitureRequise, Integer> {

	boolean existsFournitureRequiseByType(TypeFourniture typeFourniture);

	List<FournitureRequise> getAllByVersionId(int id);
}
