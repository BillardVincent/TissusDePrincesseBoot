package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;

@Repository
public interface MatiereDao extends JpaRepository<Matiere, Integer> {
    Matiere getByValue(String value);
    boolean existsByValue(String value);

}
