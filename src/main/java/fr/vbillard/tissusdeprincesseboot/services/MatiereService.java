package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatiereService extends AbstractService<Matiere>{
	private MatiereDao dao;

	@Override
	protected JpaRepository getDao() {
		return dao;
	}
	
	public Matiere findMatiere(String value) {
		return dao.getByValue(value);
	}

	public ObservableList<String>getAllMatieresValues(){
		List<Matiere> lst = getAll();
		return FXCollections.observableArrayList(lst.stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList()));
	}
	
	public boolean validate(String value) {
		return !dao.existsByValue(value);
	}
	
	public void delete(String value) {
		delete(findMatiere(value));
	}

}
