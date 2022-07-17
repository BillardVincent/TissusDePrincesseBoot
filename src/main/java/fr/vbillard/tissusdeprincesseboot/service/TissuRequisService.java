package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.CalculPoidsTissuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuRequisService {

	private TissusRequisDao tissuRequisDao;
	@Lazy
	private TissuVariantService tvs;
	private ModelMapper mapper;
	private UserPrefService userPrefService;

	private CalculPoidsTissuService calculPoidsTissuService;

	public List<TissuRequis> getAllTissuRequisByPatron(int id) {
		return tissuRequisDao.getAllByPatronId(id);
	}

	public List<TissuRequisDto> getAllTissuRequisDtoByPatron(int id) {
		return tissuRequisDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr, TissuRequisDto.class))
				.collect(Collectors.toList());
	}

	public TissuRequisDto createOrUpdate(TissuRequisDto tissu, PatronDto patron) {
		TissuRequis t = mapper.map(tissu, TissuRequis.class);
		t.setPatron(mapper.map(patron, Patron.class));
		return mapper.map(tissuRequisDao.save(t), TissuRequisDto.class);

	}

	public TissuRequis findTissuRequis(int tissuRequisId) {

		return tissuRequisDao.findById(tissuRequisId).get();
	}

	public void delete(TissuRequisDto tissu) {
		delete(mapper.map(tissu, TissuRequis.class));

	}

	public void delete(TissuRequis tissu) {
		List<TissuVariant> tvLst = tvs.getVariantByTissuRequis(tissu);
		for (TissuVariant tv : tvLst) {
			tvs.delete(tv);
		}
		tissuRequisDao.delete(tissu);

	}

	public ObservableList<TissuRequis> getAsObservableAllTissuRequisByPatron(int id) {
		return FXCollections.observableArrayList(getAllTissuRequisByPatron(id));
	}

	public TissuSpecification getTissuSpecification(TissuRequisDto tr) {
		return getTissuSpecification(mapper.map(tr, TissuRequis.class));
	}

	public TissuSpecification getTissuSpecification(TissuRequis tr) {

		float marge = userPrefService.getUser().getLongueurMargePercent();

		NumericSearch<Integer> longueurSearch = new NumericSearch<Integer>();
		longueurSearch.setGreaterThanEqual(Math.round(tr.getLongueur() - tr.getLongueur() * marge));

		NumericSearch<Integer> laizeSearch = new NumericSearch<Integer>();
		laizeSearch.setGreaterThanEqual(Math.round(tr.getLaize() - tr.getLaize() * marge));

		NumericSearch<Integer> poidsSearch = calculPoidsTissuService.getNumericSearch(tr.getGammePoids());

		List<Matiere> matieres = new ArrayList<Matiere>();
		List<TypeTissuEnum> types = new ArrayList<TypeTissuEnum>();

		if (tr.getTissuVariants() != null) {
			for (TissuVariant tv : tr.getTissuVariants()) {
				matieres.add(tv.getMatiere());
				types.add(tv.getTypeTissu());
			}
		}
		return TissuSpecification.builder().longueur(longueurSearch).laize(laizeSearch).poids(poidsSearch)
				.matieres(matieres).typeTissu(types).build();
	}

}
