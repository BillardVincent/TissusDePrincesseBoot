package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;

@Repository
public interface TissuVariantDao extends JpaRepository<TissuVariant, Integer> {
	
	List<TissuVariant> getAllByTissuRequisId(int id);

    boolean existsTissuVariantByMatiere(Matiere matiere);

    boolean existsTissuVariantByTypeTissu(TypeTissu typeTissu);

    boolean existsTissuVariantByTissage(Tissage tissage);
}
