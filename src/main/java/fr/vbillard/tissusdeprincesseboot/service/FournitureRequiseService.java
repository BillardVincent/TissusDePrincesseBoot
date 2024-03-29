package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FournitureRequiseService
		extends AbstractRequisService<FournitureRequise, Fourniture, FournitureRequiseDto> {

	private final FournitureRequiseDao fournitureRequiseDao;
	private final UserPrefService userPrefService;

	public FournitureRequiseService(MapperService mapper, FournitureRequiseDao fournitureRequiseDao,
			UserPrefService userPrefService) {
		super(mapper);
		this.fournitureRequiseDao = fournitureRequiseDao;
		this.userPrefService = userPrefService;
	}

	public List<FournitureRequise> getAllByVersionId(int id) {
		return fournitureRequiseDao.getAllByVersionId(id);
	}

	public List<FournitureRequiseDto> getAllFournitureRequiseDtoByVersion(int id) {
		return fournitureRequiseDao.getAllByVersionId(id).stream().map(tr -> mapper.map(tr))
				.toList();
	}

	public FournitureRequise findFournitureRequise(int fournitureRequisId) {

		return fournitureRequiseDao.findById(fournitureRequisId).get();
	}

	@Transactional
	public void delete(FournitureRequiseDto fourniture) {
		delete(mapper.map(fourniture));

	}

	@Override
	@Transactional
	public FournitureRequise createNewForPatron(int patronId) {
		FournitureRequise fr = new FournitureRequise();
		fr.setVersion(patronVersionService.getById(patronId));
		return saveOrUpdate(fr);
	}

	@Override
	protected void beforeSaveOrUpdate(FournitureRequise entity) {
		if (entity.getVersion() == null) {
			FournitureRequise original = getById(entity.getId());
			entity.setVersion(original.getVersion());
		}
	}

	/*
	 * @Override public void beforeDelete(FournitureRequise fourniture) {
	 * PatronVersion pv = fourniture.getVersion();
	 * pv.getFournituresRequises().remove(fourniture);
	 * patronVersionService.saveOrUpdate(pv);
	 * 
	 * delete(fourniture);
	 * 
	 * }
	 * 
	 */

	public ObservableList<FournitureRequise> getAsObservableAllFournitureRequiseByVersion(int id) {
		return FXCollections.observableArrayList(getAllByVersionId(id));
	}

	public FournitureSpecification getFournitureSpecification(FournitureRequiseDto tr) {
		return getFournitureSpecification(convert(tr));
	}

	public FournitureSpecification getFournitureSpecification(FournitureRequise fr) {

		float marge = userPrefService.getUser().getLongueurMargePercent();

		NumericSearch<Float> quantiteSearch = new NumericSearch<>();
		quantiteSearch.setGreaterThanEqual(fr.getQuantite() - fr.getQuantite() * marge);

		List<TypeFourniture> types = new ArrayList<>();
		types.add(fr.getType());

		NumericSearch<Float> quantiteSecondaire = null;

		if (fr.getType().getDimensionSecondaire() != null) {
			quantiteSecondaire = new NumericSearch<>();
			quantiteSecondaire.setGreaterThanEqual(fr.getQuantiteSecMin());
			quantiteSecondaire.setLessThanEqual(fr.getQuantiteSecMax());
		}

		return FournitureSpecification.builder().type(types).quantite(quantiteSearch)
				.quantiteSecondaire(quantiteSecondaire).build();
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
	public FournitureRequiseDao getDao() {
		return fournitureRequiseDao;
	}

	public FournitureRequise duplicate(int id, PatronVersion version) {
		FournitureRequise source = getById(id);
		FournitureRequise clone = new FournitureRequise();
		clone.setDetails(source.getDetails());
		clone.setQuantite(source.getQuantite());
		clone.setQuantiteSecMax(source.getQuantiteSecMax());
		clone.setQuantiteSecMin(source.getQuantiteSecMin());
		clone.setType(source.getType());
		clone.setUnite(source.getUnite());
		clone.setUniteSecondaire(source.getUniteSecondaire());
		clone.setVersion(version);
		saveOrUpdate(clone);
		
		return clone;
	}
}
