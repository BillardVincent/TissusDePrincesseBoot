package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.ProjetDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ProjetService extends AbstractDtoService<Projet, ProjetDto> {
	MapperService mapper;
	ProjetDao dao;

	@Override
	protected void beforeSaveOrUpdate(Projet entity) {

	}

	@Override
	protected ProjetDao getDao() {
		return dao;
	}

	@Override
	public Projet convert(ProjetDto dto) {
		return mapper.map(dto);
	}

	@Override
	public ProjetDto convert(Projet entity) {
		return mapper.map(entity);
	}

	@Override
	public ProjetDto saveOrUpdate(ProjetDto dto) {
		return convert(saveOrUpdate(convert(dto)));
	}

	public ObservableList<ProjetDto> getObservableList() {
		return FXCollections.observableArrayList(
				dao.findAll().stream().map(this::convert).collect(Collectors.toList()));
	}

	public ProjetDto newProjetDto(PatronDto selectedPatron) {
		Projet p = new Projet();
		p.setPatron(mapper.map(selectedPatron));

		return convert(p);
	}

	@Transactional
	public ObservableList<ProjetDto> getObservablePage(int page, int pageSize) {
		Page<Projet> projects = dao.findAll(PageRequest.of(page, pageSize));
		return projectListToObservableList(projects);
	}

	@Transactional
	public List<ProjetDto> getObservablePage(int page, int pageSize, ProjetSpecification specification) {
		Page<Projet> projects = dao.findAll(specification, PageRequest.of(page, pageSize));
		return projectListToObservableList(projects);
	}

	private ObservableList<ProjetDto> projectListToObservableList(Page<Projet> projects) {
		if(projects.hasContent()){
			return FXCollections.observableArrayList(projects.stream()
					.map(this::convert).collect(Collectors.toList()));
		}
		return FXCollections.emptyObservableList();
	}

}
