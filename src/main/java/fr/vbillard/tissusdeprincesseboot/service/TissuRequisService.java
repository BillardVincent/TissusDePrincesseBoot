package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.CalculPoidsTissuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TissuRequisService extends AbstractRequisService<TissuRequis, Tissu, TissuRequisDto>{

	private final TissusRequisDao tissuRequisDao;
	private final UserPrefService userPrefService;
	private final CalculPoidsTissuService calculPoidsTissuService;

	@Override
	public List<TissuRequis> getAllByVersionId(int id) {
		return tissuRequisDao.getAllByVersionId(id);
	}

	public List<TissuRequisDto> getAllTissuRequisDtoByPatron(int id) {
		return tissuRequisDao.getAllByVersionId(id).stream().map(tr -> mapper.map(tr))
				.toList();
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
		tr.setVersion(patronVersionDao.getById(patronId));
		return saveOrUpdate(tr);
	}

	@Override
	public TissusRequisDao getDao() {
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

	
	public TissuRequis duplicate(int id) {
		return duplicate (id, null);
	}

	public TissuRequis duplicate(int id, PatronVersion version) {
		TissuRequis source = getById(id);
		TissuRequis clone = new TissuRequis();
		
		clone.setDoublure(source.isDoublure());
		clone.setTissages(new ArrayList<>(source.getTissages()));
		clone.setGammePoids(new ArrayList<>(source.getGammePoids()));
		clone.setMatieres(new ArrayList<>(source.getMatieres()));
		clone.setTypeTissu(source.getTypeTissu());
		if ( version == null) {
			clone.setVersion(source.getVersion());
		} else {
			clone.setVersion(version);
		}

		
		if (!CollectionUtils.isEmpty(source.getOption())){
			clone.setOption(new ArrayList<>());
			for (TissuRequisLaizeOption trloSource : source.getOption()) {
				TissuRequisLaizeOption trloClone = new TissuRequisLaizeOption();
				trloClone.setLaize(trloSource.getLaize());
				trloClone.setLongueur(trloSource.getLongueur());
				trloClone.setRequis(clone);
				clone.getOption().add(trloClone);
			}
		}
		return saveOrUpdate(clone);
			
	}
}
