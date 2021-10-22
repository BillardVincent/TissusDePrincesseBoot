package fr.vbillard.tissusdeprincesseboot.services;

import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.ProjetDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Service
public class ProjetService extends AbstractService<Projet>{
ModelMapper mapper;
ProjetDao dao;
	
	@Override
	protected JpaRepository getDao() {
		return dao;
	}

	public ProjetDto saveOrUpdate(ProjetDto dto) {
		return mapper.map(saveOrUpdate(mapper.map(dto, Projet.class)), ProjetDto.class);
	}
	
	public ObservableList<ProjetDto> getObservableList(){
		return FXCollections.observableArrayList(dao.findAll().stream().map(t -> mapper.map(t, ProjetDto.class)).collect(Collectors.toList()));
	}

	public ProjetDto newProjetDto(PatronDto selectedPatron) {
		Projet p = new Projet();
		p.setPatron(mapper.map(selectedPatron, Patron.class));
		
		return mapper.map(p, ProjetDto.class);
	}


}
