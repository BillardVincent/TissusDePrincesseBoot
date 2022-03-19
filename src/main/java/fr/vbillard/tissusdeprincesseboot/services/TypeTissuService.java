package fr.vbillard.tissusdeprincesseboot.services;

import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.dao.TypeTissuDao;
import fr.vbillard.tissusdeprincesseboot.exception.CantBeDeletedException;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TypeTissuService extends AbstractService<TypeTissu> {
	private TypeTissuDao typeTissuDao;
	private TissuDao tissuDao;
	private TissuVariantDao tissuVariantDao;

	public TypeTissu findTypeTissu(String typeTissu) {
		return typeTissuDao.getByValue(typeTissu);
	}

	public ObservableList<TypeTissu> getAllAsObservable() {
		return FXCollections.observableArrayList(getAll());
	}

	public boolean validate(String text) {
		return !typeTissuDao.existsByValue(text);
	}

	public void delete(String text) {
		TypeTissu typeTissu = findTypeTissu(text);
		checkIfTypeTissuIsUsed(typeTissu);
		delete(typeTissu);

	}

	public void checkIfTypeTissuIsUsed(TypeTissu typeTissu) {
		if (tissuDao.existsTissuByTypeTissu(typeTissu)){
			throw new CantBeDeletedException(typeTissu, null);
		}
		if (tissuVariantDao.existsTissuVariantByTypeTissu(typeTissu)){
			throw new CantBeDeletedException(typeTissu, null);
		}
	}

	public ObservableList<String> getAllTypeTissuValues() {
		return FXCollections
				.observableArrayList(getAll().stream().map(TypeTissu::getValue).collect(Collectors.toList()));
	}

	@Override
	protected JpaRepository getDao() {
		return typeTissuDao;
	}

}
