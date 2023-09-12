package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatiereDao extends Idao<Matiere, Integer> {
	Matiere getByValue(String value);

	boolean existsByValue(String value);

	List<Matiere> findByValueIn(List<String> values);

}
