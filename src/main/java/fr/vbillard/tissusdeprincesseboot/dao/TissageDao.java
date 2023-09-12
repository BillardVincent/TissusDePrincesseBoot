package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TissageDao extends Idao<Tissage, Integer> {
	Tissage getByValue(String value);

	boolean existsByValue(String value);

	List<Tissage> findByValueIn(List<String> values);
}
