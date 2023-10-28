package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.PatronDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PatronService extends AbstractDtoService<Patron, PatronDto> {

	PatronDao patronDao;
	ModelMapper mapper;
	TissusRequisDao tissuRequisDao;

	@Transactional
	public PatronDto create(PatronDto patron) {
		Patron p = convert(patron);
		return convert(saveOrUpdate(p));
	}

	public boolean existByReference(String string) {
		return patronDao.existsByReference(string);
	}

	public void delete(PatronDto selected) {
		tissuRequisDao.deleteAll(tissuRequisDao.getAllByVersionId(selected.getId()));
		delete(mapper.map(selected, Patron.class));
	}

	@Override
	protected void beforeSaveOrUpdate(Patron entity) {
		//nothing to do here
	}

	@Override
	protected PatronDao getDao() {
		return patronDao;
	}

	@Transactional
	public ObservableList<PatronDto> getObservablePage(int page, int pageSize) {
		Page<Patron> patrons = patronDao.getAllByArchived(PageRequest.of(page, pageSize), false);
		return projectListToObservableList(patrons);
	}

	@Transactional
	public List<PatronDto> getObservablePage(int page, int pageSize, PatronSpecification specification) {
		Page<Patron> patrons = patronDao.findAll(specification, PageRequest.of(page, pageSize));
		return projectListToObservableList(patrons);
	}

	private ObservableList<PatronDto> projectListToObservableList(Page<Patron> patrons) {
		if(patrons.hasContent()){
			return FXCollections.observableArrayList(patrons.stream()
					.map(this::convert).collect(Collectors.toList()));
		}
		return FXCollections.emptyObservableList();
	}
	@Transactional
	@Override
	public PatronDto saveOrUpdate(PatronDto dto) {
		Patron patron = convert(dto);
		patron = saveOrUpdate(patron);
		return convert(patron);
	}

	@Override
	public Patron convert(PatronDto dto) {
		return mapper.map(dto, Patron.class);
	}

	@Override
	public PatronDto convert(Patron dto) {
		return mapper.map(dto, PatronDto.class);
	}

	public Patron getPatronByProjectId(int id) {
		return patronDao.getPatronByProjetId(id);
	}

	public int countByRangementId(int id) {
		return getDao().countByRangementIdAndArchived(id, false);
	}

    public PatronDto addRangement(int id, Rangement rangement) {
	    Patron p = getById(id);
	    p.setRangement(rangement);
	    saveOrUpdate(p);
	    return convert(p);

    }
}
