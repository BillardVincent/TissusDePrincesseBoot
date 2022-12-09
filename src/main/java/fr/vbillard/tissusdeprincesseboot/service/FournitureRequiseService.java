package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FournitureRequiseService {

	private FournitureRequiseDao fournitureRequiseDao;
	@Lazy
	private FournitureVariantService tvs;
	private ModelMapper mapper;
	private UserPrefService userPrefService;

	public List<FournitureRequise> getAllFournitureRequiseByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id);
	}

	public List<FournitureRequiseDto> getAllFournitureRequiseDtoByPatron(int id) {
		return fournitureRequiseDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr, FournitureRequiseDto.class))
				.collect(Collectors.toList());
	}

	public FournitureRequiseDto createOrUpdate(FournitureRequiseDto fourniture, PatronDto patron) {
		FournitureRequise t = mapper.map(fourniture, FournitureRequise.class);
		t.setPatron(mapper.map(patron, Patron.class));
		return mapper.map(fournitureRequiseDao.save(t), FournitureRequiseDto.class);

	}

	public FournitureRequise findFournitureRequise(int fournitureRequisId) {

		return fournitureRequiseDao.findById(fournitureRequisId).get();
	}

	public void delete(FournitureRequiseDto fourniture) {
		delete(mapper.map(fourniture, FournitureRequise.class));

	}

	public void delete(FournitureRequise fourniture) {
		List<FournitureVariant> tvLst = tvs.getVariantByFournitureRequise(fourniture);
		for (FournitureVariant tv : tvLst) {
			tvs.delete(tv);
		}
		fournitureRequiseDao.delete(fourniture);

	}

	public ObservableList<FournitureRequise> getAsObservableAllFournitureRequiseByPatron(int id) {
		return FXCollections.observableArrayList(getAllFournitureRequiseByPatron(id));
	}

	public FournitureSpecification getFournitureSpecification(FournitureRequiseDto tr) {
		return getFournitureSpecification(mapper.map(tr, FournitureRequise.class));
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

		if (tr.getFournitureVariants() != null) {
			for (FournitureVariant tv : tr.getFournitureVariants()) {
				//matieres.add(tv.getMatiere());
				//types.add(tv.getTypeTissu());
			}
		}
		return FournitureSpecification.builder().type(types).build();
	}

}
