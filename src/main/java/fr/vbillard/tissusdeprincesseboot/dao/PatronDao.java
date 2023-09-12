package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronDao extends Idao<Patron, Integer> {

	boolean existsByReference(String ref);

	Page<Patron> getAllByArchived(Pageable pageable, boolean archived);


	@Query(value = "SELECT pa.* FROM PATRON pa INNER JOIN PATRON_VERSION "
			+ "pv ON pv.PATRON_ID = pa.ID INNER JOIN PROJET P on p.PATRON_VERSION_ID = pv.ID WHERE P.ID = ?1",
			nativeQuery =	true)
	Patron getPatronByProjetId(int id);
}
