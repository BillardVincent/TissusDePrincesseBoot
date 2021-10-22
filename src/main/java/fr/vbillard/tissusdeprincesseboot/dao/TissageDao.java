package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;

@Repository
public interface TissageDao extends JpaRepository<Tissage, Integer> {
    Tissage getByValue(String value);
    boolean existsByValue(String value);
}
