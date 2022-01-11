package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.PatronDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Service
public class PatronService extends AbstractService<Patron>{
	
	PatronDao patronDao;
	ModelMapper mapper;
	TissusRequisDao tissuRequisDao;
	TissuRequisService tissuRequisService;

	public PatronDto create(PatronDto patron) {
		Patron p = mapper.map(patron, Patron.class);
		return mapper.map(saveOrUpdate(p), PatronDto.class);
	}

	public boolean existByReference(String string) {
		return patronDao.existsByReference(string);
	}

	public void delete(PatronDto selected) {
		for (TissuRequis tr : tissuRequisDao.getAllByPatronId(selected.getId())) {
			tissuRequisService.delete(tr);
		}
		delete(mapper.map(selected, Patron.class));
	}

	public ObservableList<PatronDto> getObservableList(){
		List<Patron> lstPatron = getDao().findAll();
		List<PatronDto> list = lstPatron.stream().map(t -> mapper.map(t, PatronDto.class)).collect(Collectors.toList());
		return FXCollections.observableArrayList(list);
	}

	@Override
	protected JpaRepository getDao() {
		return patronDao;
	}

	public ObservableList<PatronDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(patronDao
				.findAll(PageRequest.of(page, pageSize))
				.stream().map(t -> mapper.map(t, PatronDto.class))
				.collect(Collectors.toList()));
	}
}
