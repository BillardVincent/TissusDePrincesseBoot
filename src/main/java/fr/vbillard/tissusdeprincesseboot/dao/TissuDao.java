package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TissuDao extends Idao<Tissu, Integer> {

	boolean existsByReference(String reference);

	boolean existsTissuByMatiere(Matiere matiere);

	List<Tissu> getTissuByMatiere(Matiere matiere);

	boolean existsTissuByTypeTissu(TypeTissuEnum typeTissu);

	boolean existsTissuByTissage(Tissage tissage);

	@Query(value = "SELECT SUM(tu.longueur) FROM TISSU_USED tu "
			+ "INNER JOIN TISSU t ON tu.TISSU_ID = t.ID "
			+ "INNER JOIN PROJET p ON p.ID = tu.PROJET_ID "
			+ "WHERE (p.STATUS = 'EN_COURS' OR p.STATUS = 'PLANIFIE' OR "
			+ "(p.STATUS = 'TERMINE' AND ("
			+ "SELECT COUNT(*) FROM INVENTAIRE i WHERE i.PROJET_ID = p.ID > 0)))"
			+ "AND t.ID = ?1", nativeQuery = true)
	Integer longueurUtilisee(int tissuId);

	Page<Tissu> findAllByArchived(Pageable pageable, boolean archived);

    int countByRangementIdAndArchived(int id, boolean archived);
}
