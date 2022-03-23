package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;

@Repository
public interface PhotoDao extends JpaRepository<Photo, Integer> {

	 List<Photo> getAllByTissu(Tissu tissu);

	Optional<Photo> getByTissu(Tissu tissu);

	@Query(value = "SELECT p.* FROM PHOTO p "
			+ "INNER JOIN TISSU_USED t ON t.TISSU_ID = p.TISSU_ID "
			+ "WHERE t.PROJET_ID = ?1 and t.LONGUEUR  = "
			+ "(SELECT MAX(tu2.LONGUEUR) FROM TISSU_USED tu2 "
			+ "WHERE tu2.PROJET_ID = ?1) "
			+ "LIMIT 1", nativeQuery = true)
	Optional<Photo> getByProjet(int projetId);

}
