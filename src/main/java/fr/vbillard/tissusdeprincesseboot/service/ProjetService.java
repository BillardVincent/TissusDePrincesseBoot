package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.ProjetDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
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
public class ProjetService extends AbstractService<Projet> {
	ModelMapper mapper;
	ProjetDao dao;

	@Override
	protected void beforeSaveOrUpdate(Projet entity) {

	}

	@Override
	protected ProjetDao getDao() {
		return dao;
	}

	public ProjetDto saveOrUpdate(ProjetDto dto) {
		return mapper.map(saveOrUpdate(mapper.map(dto, Projet.class)), ProjetDto.class);
	}

	public ObservableList<ProjetDto> getObservableList() {
		return FXCollections.observableArrayList(
				dao.findAll().stream().map(t -> mapper.map(t, ProjetDto.class)).collect(Collectors.toList()));
	}

	public ProjetDto newProjetDto(PatronDto selectedPatron) {
		Projet p = new Projet();
		p.setPatron(mapper.map(selectedPatron, Patron.class));

		return mapper.map(p, ProjetDto.class);
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
					.map(t -> mapper.map(t, ProjetDto.class)).collect(Collectors.toList()));
		}
		return FXCollections.emptyObservableList();
	}

}