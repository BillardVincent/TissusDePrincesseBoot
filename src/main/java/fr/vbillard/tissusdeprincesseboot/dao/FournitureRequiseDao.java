package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;

@Repository
public interface FournitureRequiseDao extends Idao<FournitureRequise, Integer> {

	boolean existsFournitureRequiseByType(TypeFourniture typeFourniture);

	List<FournitureRequise> getAllByPatronId(int id);
}
