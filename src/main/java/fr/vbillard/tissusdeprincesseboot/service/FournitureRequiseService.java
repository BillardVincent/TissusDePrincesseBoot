package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
public class FournitureRequiseService extends AbstractRequisService<FournitureRequise, Fourniture, FournitureRequiseDto> {

	private final FournitureRequiseDao fournitureRequiseDao;
	private final FournitureVariantService tvs;
	private final UserPrefService userPrefService;
	private final PatronService patronService;

	public FournitureRequiseService(MapperService mapper, FournitureRequiseDao fournitureRequiseDao,
			FournitureVariantService tvs, UserPrefService userPrefService, PatronService patronService) {
		super(mapper);
		this.fournitureRequiseDao = fournitureRequiseDao;
		this.tvs = tvs;
		this.userPrefService = userPrefService;
		this.patronService = patronService;
	}

	public List<FournitureRequise> getAllRequisByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id);
	}

	public List<FournitureRequiseDto> getAllFournitureRequiseDtoByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr))
				.collect(Collectors.toList());
	}

	public FournitureRequiseDto createOrUpdate(FournitureRequiseDto fourniture, PatronDto patron) {
		FournitureRequise t = convert(fourniture);
		t.setPatron(patronService.convert(patron));
		return convert(fournitureRequiseDao.save(t));

	}

	public FournitureRequise findFournitureRequise(int fournitureRequisId) {

		return fournitureRequiseDao.findById(fournitureRequisId).get();
	}

	public void delete(FournitureRequiseDto fourniture) {
		delete(mapper.map(fourniture));

	}

	@Override
	protected void beforeSaveOrUpdate(FournitureRequise entity) {

	}

	@Transactional
	@Override
	public void delete(FournitureRequise fourniture) {
		Patron p = fourniture.getPatron();
		p.getFournituresRequises().remove(p);
		patronService.saveOrUpdate(p);

		List<FournitureVariant> tvLst = tvs.getVariantByRequis(fourniture);
		for (FournitureVariant tv : tvLst) {
			tvs.delete(tv);
		}
		fournitureRequiseDao.delete(fourniture);

	}

	public ObservableList<FournitureRequise> getAsObservableAllFournitureRequiseByPatron(int id) {
		return FXCollections.observableArrayList(getAllRequisByPatron(id));
	}

	public FournitureSpecification getFournitureSpecification(FournitureRequiseDto tr) {
		return getFournitureSpecification(mapper.map(tr));
	}

	public FournitureSpecification getFournitureSpecification(FournitureRequise tr) {

		float marge = userPrefService.getUser().getLongueurMargePercent();

		/*
		TODO
		NumericSearch<Integer> longueurSearch = new NumericSearch<Integer>();
		longueurSearch.setGreaterThanEqual(Math.round(tr.getLongueur() - tr.getLongueur() * marge));

		NumericSearch<Integer> laizeSearch = new NumericSearch<Integer>();
		laizeSearch.setGreaterThanEqual(Math.round(tr.getLaize() - tr.getLaize() * marge));

		NumericSearch<Integer> poidsSearch = calculPoidsTissuService.getNumericSearch(tr.getGammePoids());


		 */
		List<TypeFourniture> types = new ArrayList<TypeFourniture>();

		return FournitureSpecification.builder().type(types).build();
	}

	@Override
	public FournitureRequise convert(FournitureRequiseDto dto) {
		FournitureRequise result =  mapper.map(dto);
		return result;
	}

	@Override
	public FournitureRequiseDto convert(FournitureRequise entity) {
		FournitureRequiseDto dto = mapper.map(entity);
		return dto;
	}

	@Override
	protected FournitureRequiseDao getDao() {
		return fournitureRequiseDao;
	}
}
