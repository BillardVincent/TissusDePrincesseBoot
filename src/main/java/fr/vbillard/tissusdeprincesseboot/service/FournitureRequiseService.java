package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
public class FournitureRequiseService extends AbstractRequisService<FournitureRequise, Fourniture, FournitureRequiseDto> {

	private final FournitureRequiseDao fournitureRequiseDao;
	private final FournitureVariantService tvs;
	private final UserPrefService userPrefService;

	public FournitureRequiseService(MapperService mapper, FournitureRequiseDao fournitureRequiseDao,
			FournitureVariantService tvs, UserPrefService userPrefService) {
		super(mapper);
		this.fournitureRequiseDao = fournitureRequiseDao;
		this.tvs = tvs;
		this.userPrefService = userPrefService;
	}

	public List<FournitureRequise> getAllRequisByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id);
	}

	public List<FournitureRequiseDto> getAllFournitureRequiseDtoByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr))
				.collect(Collectors.toList());
	}

	public FournitureRequiseDto createOrUpdate(FournitureRequiseDto fourniture, PatronDto patron) {
		FournitureRequise t = mapper.map(fourniture);
		t.setPatron(mapper.map(patron));
		return mapper.map(fournitureRequiseDao.save(t));

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

	public void delete(FournitureRequise fourniture) {
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
		List<Matiere> matieres = new ArrayList<Matiere>();
		List<TypeFourniture> types = new ArrayList<TypeFourniture>();

		if (tr.getVariants() != null) {
			for (FournitureVariant tv : tr.getVariants()) {
				//matieres.add(tv.getMatiere());
				//types.add(tv.getTypeTissu());
			}
		}
		return FournitureSpecification.builder().type(types).build();
	}

	@Override
	public FournitureRequise convert(FournitureRequiseDto dto) {
		return mapper.map(dto);
	}

	@Override
	public FournitureRequiseDto convert(FournitureRequise entity) {
		return mapper.map(entity);
	}

	@Override
	protected FournitureRequiseDao getDao() {
		return fournitureRequiseDao;
	}
}
