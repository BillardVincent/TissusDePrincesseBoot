package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;

@Repository
public interface MatiereDao extends Idao<Matiere, Integer> {
	Matiere getByValue(String value);

	boolean existsByValue(String value);

}
