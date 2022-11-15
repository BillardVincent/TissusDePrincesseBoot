package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;

@Repository
public interface TypeFournitureDao extends Idao<TypeFourniture, Integer> {
	TypeFourniture getByValue(String value);

	boolean existsByValue(String value);

	List<TypeFourniture> findByValueIn(List<String> values);

	boolean existsByValueAndNotById(String value, int id);

}
