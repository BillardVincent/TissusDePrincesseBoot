package fr.vbillard.tissusdeprincesseboot.services;

import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.exception.CantBeDeletedException;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TissageService extends AbstractService<Tissage>{
	private TissageDao dao;
	private TissuDao tissuDao;
	private TissuVariantDao tissuVariantDao;

	public Tissage findTissage(String value) {
		return dao.getByValue(value);
	}
	
	public ObservableList<String> getAllObs() {
		return FXCollections.observableArrayList(getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList()));
	}

	public boolean validate(String value) {
		return !dao.existsByValue(value);		
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}


	public void delete(String value) {
		Tissage tissage = findTissage(value);
		checkIfTissageIsUsed(tissage);
		delete(tissage);
	}

	public void checkIfTissageIsUsed(Tissage tissage) {
		if (tissuDao.existsTissuByTissage(tissage)){
			throw new CantBeDeletedException(tissage, null);
		}
		if (tissuVariantDao.existsTissuVariantByTissage(tissage)){
			throw new CantBeDeletedException(tissage, null);
		}
	}
	
	

}
