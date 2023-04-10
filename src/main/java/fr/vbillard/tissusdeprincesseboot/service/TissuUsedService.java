package fr.vbillard.tissusdeprincesseboot.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
		//Nothing to do
	}

	@Override
	protected TissuUsedDao getDao() {
		return dao;
	}

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequisDto tissuRequis, ProjetDto projet) {
		return getTissuUsedByTissuRequisAndProjet(mapper.map(tissuRequis, TissuRequis.class),
				mapper.map(projet, Projet.class));
	}

	public int longueurVariantByRequis(TissuRequisDto tissuRequis, ProjetDto projet){
		List<TissuUsed> lst = getTissuUsedByTissuRequisAndProjet(tissuRequis, projet);
		if (CollectionUtils.isEmpty(lst)){
			return 0;
		}
		return lst.stream().map(TissuUsed::getLongueur).reduce(0, Integer::sum);
	}

	@Transactional
	public List<TissuUsed> getTissuVariantLaizeTooShort(TissuRequisDto tissuRequis, ProjetDto projet){
		List<TissuUsed> lst = getTissuUsedByTissuRequisAndProjet(tissuRequis, projet);
		if (CollectionUtils.isEmpty(lst)){
			return Collections.emptyList();
		}
		return lst.stream().filter(u -> u.getTissu().getLaize() < tissuRequis.getLaize()).collect(Collectors.toList());
	}

	public boolean existsByTissuId(int id) {
		return dao.existsByTissuId(id);
	}

}
