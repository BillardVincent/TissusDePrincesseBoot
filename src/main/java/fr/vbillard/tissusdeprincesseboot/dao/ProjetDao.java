package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Projet;

@Repository
public interface ProjetDao  extends JpaRepository<Projet, Integer> {

	
}
