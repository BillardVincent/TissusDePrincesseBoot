package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TissusRequisDao extends Idao<TissuRequis, Integer> {

	List<TissuRequis> getAllByVersionId(int id);

}
