package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;

public interface TissuDao extends JpaRepository<Tissu, Integer> {

	boolean existsByReference(String reference);

	boolean existsTissuByMatiere(Matiere matiere);

	List<Tissu> getTissuByMatiere(Matiere matiere);

	boolean existsTissuByTypeTissu(TypeTissuEnum typeTissu);

	boolean existsTissuByTissage(Tissage tissage);
}
