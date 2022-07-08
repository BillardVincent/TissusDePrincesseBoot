package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;

@Repository
public interface TissusRequisDao extends Idao<TissuRequis, Integer> {

	List<TissuRequis> getAllByPatronId(int id);

}
