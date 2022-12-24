package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuUsedService extends AbstractUsedService<TissuUsed, Tissu> {

	private ModelMapper mapper;
	private TissuUsedDao dao;

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequis tr, Projet p) {
		return dao.getAllByRequisAndProjet(tr, p);
	}

	public List<TissuUsed> getByTissu(Tissu t) {
		return dao.getAllByTissu(t);
	}

	public List<TissuUsed> getByProjet(Projet p) {
		return dao.getAllByProjet(p);
	}

	@Override
	protected void beforeSaveOrUpdate(TissuUsed entity) {

	}

	@Override
	protected TissuUsedDao getDao() {
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
