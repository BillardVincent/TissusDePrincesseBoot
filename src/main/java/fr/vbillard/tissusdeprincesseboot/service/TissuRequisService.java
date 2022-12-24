package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.CalculPoidsTissuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuRequisService extends AbstractRequisService<TissuRequis, Tissu, TissuRequisDto>{

	private TissusRequisDao tissuRequisDao;
	@Lazy
	private TissuVariantService tvs;
	private UserPrefService userPrefService;

	private CalculPoidsTissuService calculPoidsTissuService;

	@Override
	public List<TissuRequis> getAllRequisByPatron(int id) {
		return tissuRequisDao.getAllByPatronId(id);
	}

	public List<TissuRequisDto> getAllTissuRequisDtoByPatron(int id) {
		return tissuRequisDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr))
				.collect(Collectors.toList());
	}

	public TissuRequis findTissuRequis(int tissuRequisId) {
		return tissuRequisDao.findById(tissuRequisId).orElse(new TissuRequis());
	}

	public void delete(TissuRequisDto tissu) {
		delete(mapper.map(tissu));

	}

	@Override
	protected void beforeSaveOrUpdate(TissuRequis entity) {

	}

	public void delete(TissuRequis tissu) {
		List<TissuVariant> tvLst = tvs.getVariantByRequis(tissu);
		for (TissuVariant tv : tvLst) {
			tvs.delete(tv);
		}
		tissuRequisDao.delete(tissu);

	}

	@Override
	protected TissusRequisDao getDao() {
		return tissuRequisDao;
	}

	public ObservableList<TissuRequis> getAsObservableAllTissuRequisByPatron(int id) {
		return FXCollections.observableArrayList(getAllRequisByPatron(id));
	}

	public TissuSpecification getTissuSpecification(TissuRequisDto tr) {
		return getTissuSpecification(convert(tr));
	}

	public TissuSpecification getTissuSpecification(TissuRequis tr) {

		float marge = userPrefService.getUser().getLongueurMargePercent();

		NumericSearch<Integer> longueurSearch = new NumericSearch<Integer>();
		longueurSearch.setGreaterThanEqual(Math.round(tr.getQuantite() - tr.getQuantite() * marge));

		NumericSearch<Integer> laizeSearch = new NumericSearch<Integer>();
		laizeSearch.setGreaterThanEqual(Math.round(tr.getLaize() - tr.getLaize() * marge));

		NumericSearch<Integer> poidsSearch = calculPoidsTissuService.getNumericSearch(tr.getGammePoids());

		List<Matiere> matieres = new ArrayList<Matiere>();
		List<TypeTissuEnum> types = new ArrayList<TypeTissuEnum>();

		if (tr.getVariants() != null) {
			for (TissuVariant tv : tr.getVariants()) {
				matieres.add(tv.getMatiere());
				types.add(tv.getTypeTissu());
			}
		}
		return TissuSpecification.builder().longueur(longueurSearch).laize(laizeSearch).poids(poidsSearch)
				.matieres(matieres).typeTissu(types).build();
	}

	@Override
	public TissuRequis convert(TissuRequisDto entity) {
		return mapper.map(entity);
	}

	@Override
	public TissuRequisDto convert(TissuRequis dto) {
		return mapper.map(dto);
	}
}
