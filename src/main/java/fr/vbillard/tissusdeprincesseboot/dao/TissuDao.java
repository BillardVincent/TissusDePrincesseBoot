package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;

public interface TissuDao extends Idao<Tissu, Integer> {

	boolean existsByReference(String reference);

	boolean existsTissuByMatiere(Matiere matiere);

	List<Tissu> getTissuByMatiere(Matiere matiere);

	boolean existsTissuByTypeTissu(TypeTissuEnum typeTissu);

	boolean existsTissuByTissage(Tissage tissage);

	// TODO
	@Query(value = "SELECT SUM(t.longueur) FROM TISSU_USED tu INNER JOIN TISSU t ON tu.TISSU_ID = t.ID "
			+ "INNER JOIN PROJET p ON p.ID = tu.PROJET_ID WHERE p.STATUS = 'EN_COURS' OR p.STATUS = 'PLANIFIE' "
			+ "AND t.ID = ?1", nativeQuery = true)
	Integer longueurUtilisee(int tissuId);

}
