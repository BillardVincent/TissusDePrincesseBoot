package fr.vbillard.tissusdeprincesseboot.services;

import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TypeTissuDao;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeTissuService extends AbstractService<TypeTissu> {
	TypeTissuDao typeTissuDao;


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
		delete(findTypeTissu(text));

	}

	public ObservableList<String> getAllTypeTissuValues() {
		return FXCollections
				.observableArrayList(getAll().stream().map(tt -> tt.getValue()).collect(Collectors.toList()));
	}

	@Override
	protected JpaRepository getDao() {
		return typeTissuDao;
	}

}
