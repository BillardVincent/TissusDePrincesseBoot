package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;

@Repository
public interface TissuVariantDao extends Idao<TissuVariant, Integer> {

	List<TissuVariant> getAllByRequisId(int id);

	boolean existsTissuVariantByMatiere(Matiere matiere);

	boolean existsTissuVariantByTypeTissu(TypeTissuEnum typeTissu);

	boolean existsTissuVariantByTissage(Tissage tissage);
}
