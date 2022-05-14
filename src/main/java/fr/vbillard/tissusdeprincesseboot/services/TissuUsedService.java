package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuUsedService extends AbstractService<TissuUsed> {

	private ModelMapper mapper;
	private TissuUsedDao dao;

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequis tr, Projet p) {
		return dao.getAllByTissuRequisAndProjet(tr, p);
	}

	public List<TissuUsed> getByTissu(Tissu t) {
		return dao.getAllByTissu(t);
	}

	public List<TissuUsed> getByProjet(Projet p) {
		return dao.getAllByProjet(p);
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}

	public TissuUsed saveNew(TissuRequis tr, Projet p, int longueur) {
		return null;
	}

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequisDto tissuRequis, ProjetDto projet) {
		return getTissuUsedByTissuRequisAndProjet(mapper.map(tissuRequis, TissuRequis.class),
				mapper.map(projet, Projet.class));
	}

	public boolean existsByTissuId(int id) {
		return dao.existsByTissuId(id);
	}

}
