package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.exception.CantBeDeletedException;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatiereService extends AbstractService<Matiere> {
	private MatiereDao dao;
	private TissuDao tissuDao;

	@Override
	protected void beforeSaveOrUpdate(Matiere entity) {

	}

	@Override
	protected MatiereDao getDao() {
		return dao;
	}

	public Matiere findMatiere(String value) {
		return dao.getByValue(value);
	}

	public List<Matiere> findMatiere(List<String> values) {
		List<Matiere> matieres = null;
		if (values != null && !values.isEmpty()) {
			matieres = dao.findByValueIn(values);
		}
		return matieres;
	}

	public ObservableList<String> getAllMatieresValues() {
		List<Matiere> lst = getAll();
		return FXCollections.observableArrayList(
				lst.stream().map(AbstractSimpleValueEntity::getValue).sorted().collect(Collectors.toList()));
	}

	public boolean validate(String value) {
		return !dao.existsByValue(value);
	}

	public void delete(String value) {
		Matiere matiere = findMatiere(value);
		checkIfMatiereIsUsed(matiere);
		delete(matiere);
	}

	public void checkIfMatiereIsUsed(Matiere matiere) {
		if (tissuDao.existsTissuByMatiere(matiere)) {
			throw new CantBeDeletedException(matiere, null);
		}
	}

	public List<String> getAllValues() {
		List<String> result = new ArrayList<>();
		for (Matiere m : getAll()) {
			result.add(m.getValue());
		}
		Collections.sort(result);
		return result;
	}
}
