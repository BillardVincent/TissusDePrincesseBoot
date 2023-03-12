package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureUsedDao;
import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FournitureUsedService extends AbstractUsedService<FournitureUsed, Fourniture> {

	private MapperService mapper;
	private FournitureUsedDao dao;

	public List<FournitureUsed> getFournitureUsedByFournitureRequiseAndProjet(FournitureRequise tr, Projet p) {
		return dao.getAllByRequisAndProjet(tr, p);
	}

	public List<FournitureUsed> getByFourniture(Fourniture t) {
		return dao.getAllByFourniture(t);
	}

	public List<FournitureUsed> getByProjet(Projet p) {
		return dao.getAllByProjet(p);
	}

	@Override
	protected void beforeSaveOrUpdate(FournitureUsed entity) {

	}

	@Override
	protected FournitureUsedDao getDao() {
		return dao;
	}

	public TissuUsed saveNew(TissuRequis tr, Projet p, int longueur) {
		return null;
	}

	public List<FournitureUsed> getFournitureUsedByTissuRequisAndProjet(FournitureRequiseDto fournitureRequise, ProjetDto projet) {
		return getFournitureUsedByFournitureRequiseAndProjet(mapper.map(fournitureRequise),
				mapper.map(projet));
	}

	public boolean existsByFournitureId(int id) {
		return dao.existsByFournitureId(id);
	}

}
