package fr.vbillard.tissusdeprincesseboot.services;

import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TissageService extends AbstractService<Tissage>{
	TissageDao dao;

	public Tissage findTissage(String value) {
		return dao.getByValue(value);
	}
	
	public ObservableList<String> getAllObs() {
		return FXCollections.observableArrayList(getAll().stream().map(t -> t.getValue()).collect(Collectors.toList()));
	}

	public boolean validate(String value) {
		return !dao.existsByValue(value);		
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}
	
	
	
	

}
