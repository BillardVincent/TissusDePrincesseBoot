package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.exception.CantBeDeletedException;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TissageService extends AbstractService<Tissage> {
	private TissageDao dao;
	private TissuDao tissuDao;

	public Tissage findTissage(String value) {
		return dao.getByValue(value);
	}

	public ObservableList<String> getAllObs() {
		return FXCollections.observableArrayList(
				getAll().stream().map(AbstractSimpleValueEntity::getValue).sorted().collect(Collectors.toList()));
	}

	public boolean validate(String value) {
		return !dao.existsByValue(value);
	}

	@Override
	protected void beforeSaveOrUpdate(Tissage entity) {

	}

	@Override
	public TissageDao getDao() {
		return dao;
	}

	public void delete(String value) {
		Tissage tissage = findTissage(value);
		checkIfTissageIsUsed(tissage);
		delete(tissage);
	}

	public void checkIfTissageIsUsed(Tissage tissage) {
		if (tissuDao.existsTissuByTissage(tissage)) {
			throw new CantBeDeletedException(tissage, null);
		}

	}

	public List<String> getAllValues() {
		List<String> result = new ArrayList<>();
		for (Tissage t : getAll()) {
			result.add(t.getValue());
		}
		Collections.sort(result);
		return result;
	}

	public List<Tissage> tissageValuesSelected(List<String> values) {
		List<Tissage> tissages = null;
		if (values != null && !values.isEmpty()) {
			tissages = dao.findByValueIn(values);
		}
		return tissages;
	}

}
