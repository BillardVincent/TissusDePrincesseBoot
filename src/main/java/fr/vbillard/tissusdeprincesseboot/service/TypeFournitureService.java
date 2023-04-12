package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.TypeFournitureDao;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeFournitureService extends AbstractService<TypeFourniture> {
	private TypeFournitureDao dao;
	private FournitureDao fournitureDao;
	private FournitureRequiseDao fournitureRequiseDao;

	@Override
	protected void beforeSaveOrUpdate(TypeFourniture entity) {

	}

	@Override
	protected TypeFournitureDao getDao() {
		return dao;
	}

	public TypeFourniture findTypeFourniture(String value) {
		return dao.getByValue(value);
	}

	public List<TypeFourniture> findTypeFourniture(List<String> values) {
		List<TypeFourniture> typeFourniture = null;
		if (values != null && !values.isEmpty()) {
			typeFourniture = dao.findByValueIn(values);
		}
		return typeFourniture;
	}

	public ObservableList<String> getAllTypeFournituresValues() {
		List<TypeFourniture> lst = getAll();
		return FXCollections.observableArrayList(
				lst.stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList()));
	}

	public boolean validate(String value, TypeFourniture type) {
		return type == null || type.getId() == 0 || 
		 !dao.existsByValueAndIdNot(value, type.getId());
	}

	public void delete(String value) {
		TypeFourniture matiere = findTypeFourniture(value);
		checkIfTypeFournitureIsUsed(matiere);
		delete(matiere);
	}

	public boolean checkIfTypeFournitureIsUsed(TypeFourniture typeFourniture) {
		return fournitureDao.existsFournitureByType(typeFourniture) 
				|| fournitureRequiseDao.existsFournitureRequiseByType(typeFourniture);
		
	}

	public List<String> getAllValues() {
		List<String> result = new ArrayList();
		for (TypeFourniture tf : getAll()) {
			result.add(tf.getValue());
		}
		return result;
	}

}
