package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuUsedSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuUsedService extends AbstractUsedService<TissuUsed, Tissu> {

	private MapperService mapper;
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
		return getTissuUsedByTissuRequisAndProjet(mapper.map(tissuRequis),mapper.map(projet));
	}

	public int longueurUsedByRequis(TissuRequis tissuRequis, Projet projet){
		return longueurUsedByRequis(getTissuUsedByTissuRequisAndProjet(tissuRequis, projet));
	}

	public int longueurUsedByRequis(TissuRequisDto tissuRequis, ProjetDto projet){
		return longueurUsedByRequis(getTissuUsedByTissuRequisAndProjet(tissuRequis, projet));
	}

	public int longueurUsedByRequis(List<TissuUsed> lst){
		if (CollectionUtils.isEmpty(lst)){
			return 0;
		}
		return lst.stream().map(TissuUsed::getLongueur).reduce(0, Integer::sum);
	}

	@Transactional
	public List<TissuUsed> getTissuVariantLaizeTooShort(TissuRequisDto tissuRequis, ProjetDto projet){
		// TODO patron version

		NumericSearch<Integer> laize = new NumericSearch<>();
		//laize.setLessThan(tissuRequis.getLaize());
		TissuUsedSpecification spec =
				TissuUsedSpecification.builder().tissuRequis(mapper.map(tissuRequis)).projet(mapper.map(projet)).laize(laize).build();
		return dao.findAll(spec);
	}

	public boolean existsByTissuId(int id) {
		return dao.existsByTissuId(id);
	}

	@Transactional
	public List<TissuUsed> getTissuUsedNotDecati(TissuRequisDto tissuRequis, ProjetDto projet) {
		TissuUsedSpecification spec =
				TissuUsedSpecification.builder().tissuRequis(mapper.map(tissuRequis)).projet(mapper.map(projet)).isDecati(false).build();
		return dao.findAll(spec);
	}
}
