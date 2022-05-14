package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Service
@AllArgsConstructor
public class TissuRequisService {

	private TissusRequisDao tissuRequisDao;
	@Lazy
	private TissuVariantService tvs;
	private ModelMapper mapper;

	public List<TissuRequis> getAllTissuRequisByPatron(int id){
		return tissuRequisDao.getAllByPatronId(id);
	}
	
	public List<TissuRequisDto> getAllTissuRequisDtoByPatron(int id){
		return tissuRequisDao.getAllByPatronId(id).stream().map(tr -> mapper.map(tr, TissuRequisDto.class)).collect(Collectors.toList());
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


}
