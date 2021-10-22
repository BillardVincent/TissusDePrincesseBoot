package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;

@Repository
public interface TypeTissuDao extends JpaRepository<TypeTissu, Integer> {
	TypeTissu getByValue(String value);
	boolean existsByValue(String value);
}
