package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureUsedDao;
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
		// Nothing to do here
	}

	@Override
	protected FournitureUsedDao getDao() {
		return dao;
	}

	public TissuUsed saveNew(TissuRequis tr, Projet p, int longueur) {
		return null;
	}

	public List<FournitureUsed> getFournitureUsedByFournitureRequiseAndProjet(FournitureRequiseDto fournitureRequise, ProjetDto projet) {
		return getFournitureUsedByFournitureRequiseAndProjet(mapper.map(fournitureRequise),
				mapper.map(projet));
	}

	public boolean existsByFournitureId(int id) {
		return dao.existsByFournitureId(id);
	}

	public float quantiteUsedByRequis(FournitureRequise fournitureRequise, Projet projet){
		return quantiteUsedByRequis(getFournitureUsedByFournitureRequiseAndProjet(fournitureRequise, projet));
	}

	public float quantiteUsedByRequis(FournitureRequiseDto fournitureRequise, ProjetDto projet){
		return quantiteUsedByRequis(getFournitureUsedByFournitureRequiseAndProjet(fournitureRequise, projet));
	}

	public float quantiteUsedByRequis(List<FournitureUsed> lst){
		if (CollectionUtils.isEmpty(lst)){
			return 0;
		}
		return lst.stream().map(FournitureUsed::getQuantite).reduce(0f, Float::sum);
	}

}
