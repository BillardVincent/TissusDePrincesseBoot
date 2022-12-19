package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;

@Repository
public interface FournitureVariantDao extends Idao<FournitureVariant, Integer> {

	List<FournitureVariant> getAllByRequisId(int id);

}
