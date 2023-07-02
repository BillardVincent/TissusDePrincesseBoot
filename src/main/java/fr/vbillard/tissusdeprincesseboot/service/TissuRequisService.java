package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.CalculPoidsTissuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class TissuRequisService extends AbstractRequisService<TissuRequis, Tissu, TissuRequisDto>{

	private final TissusRequisDao tissuRequisDao;
	private final UserPrefService userPrefService;
	private final CalculPoidsTissuService calculPoidsTissuService;

	public TissuRequisService(PatronVersionService patronVersionService, TissusRequisDao tissuRequisDao,
			UserPrefService userPrefService, CalculPoidsTissuService calculPoidsTissuService,
			MapperService mapper) {
		super(mapper, patronVersionService);
		this.tissuRequisDao = tissuRequisDao;
		this.userPrefService = userPrefService;
		this.calculPoidsTissuService = calculPoidsTissuService;
	}

	@Override
	public List<TissuRequis> getAllByVersionId(int id) {
		return tissuRequisDao.getAllByVersionId(id);
	}

	public List<TissuRequisDto> getAllTissuRequisDtoByPatron(int id) {
		return tissuRequisDao.getAllByVersionId(id).stream().map(tr -> mapper.map(tr))
				.collect(Collectors.toList());
	}

	public TissuRequis findTissuRequis(int tissuRequisId) {
		return tissuRequisDao.findById(tissuRequisId).orElse(new TissuRequis());
	}

	public void delete(TissuRequisDto tissu) {
		delete(mapper.map(tissu));

	}

	@Override
	@Transactional
	public TissuRequis createNewForPatron(int patronId) {
		TissuRequis tr = new TissuRequis();
		tr.setVersion(patronVersionService.getById(patronId));
		return saveOrUpdate(tr);
	}

	@Override
	protected TissusRequisDao getDao() {
		return tissuRequisDao;
	}

	public ObservableList<TissuRequis> getAsObservableAllTissuRequisByVersion(int id) {
		return FXCollections.observableArrayList(getAllByVersionId(id));
	}

	public TissuSpecification getTissuSpecification(TissuRequisDto tr) {
		return getTissuSpecification(convert(tr));
	}

	public TissuSpecification getTissuSpecification(TissuRequis tr) {
		// TODO patron version

		float marge = userPrefService.getUser().getLongueurMargePercent();

		NumericSearch<Integer> longueurSearch = new NumericSearch<>();
		//longueurSearch.setGreaterThanEqual(Math.round(tr.getLongueur() - tr.getLongueur() * marge));

		NumericSearch<Integer> laizeSearch = new NumericSearch<>();
		//laizeSearch.setGreaterThanEqual(Math.round(tr.getLaize() - tr.getLaize() * marge));

		//NumericSearch<Integer> poidsSearch = calculPoidsTissuService.getNumericSearch(tr.getGammePoids());

		List<Matiere> matieres = new ArrayList<>();
		List<TypeTissuEnum> types = new ArrayList<>();


		return TissuSpecification.builder().longueur(longueurSearch).laize(laizeSearch)
				//.poids(poidsSearch)
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
