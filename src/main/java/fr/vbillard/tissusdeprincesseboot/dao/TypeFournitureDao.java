package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeFournitureDao extends Idao<TypeFourniture, Integer> {
	TypeFourniture getByValue(String value);

	boolean existsByValue(String value);

	List<TypeFourniture> findByValueIn(List<String> values);

	boolean existsByValueAndIdNot(String value, int id);

}
