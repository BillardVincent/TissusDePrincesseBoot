package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;

@Repository
public interface FournitureRequiseDao extends Idao<FournitureRequise, Integer>{

  boolean existsFournitureRequiseByType(TypeFourniture typeFourniture);
}
