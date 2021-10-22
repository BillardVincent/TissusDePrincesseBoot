package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuUsedService extends AbstractService<TissuUsed>{
	
	private TissuUsedDao dao;

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequis tr, Projet p) {
		return dao.getAllByTissuRequisAndProjet(tr, p);
	}

	public List<TissuUsed> getByTissu(Tissu t) {
		return dao.getAllByTissu(t);
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}

	


}
