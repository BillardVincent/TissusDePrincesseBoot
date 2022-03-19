package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;

import java.util.List;

public interface TissuDao extends JpaRepository<Tissu, Integer> {
	
	boolean existsByReference(String reference);

    boolean existsTissuByMatiere(Matiere matiere);
    List<Tissu> getTissuByMatiere(Matiere matiere);

    boolean existsTissuByTypeTissu(TypeTissu typeTissu);

    boolean existsTissuByTissage(Tissage tissage);
}
