package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Tissage;

@Repository
public interface TissageDao extends Idao<Tissage, Integer> {
	Tissage getByValue(String value);

	boolean existsByValue(String value);

	List<Tissage> findByValueIn(List<String> values);
}
