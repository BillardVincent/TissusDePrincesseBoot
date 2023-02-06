package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public abstract class AbstractService<T> {

	protected void beforeSaveOrUpdate(T entity){

	}

	protected void beforeDelete(T entity){

	}


	@Transactional
	public T saveOrUpdate(T entity) {
		beforeSaveOrUpdate(entity);
		return getDao().save(entity);
	}

	public List<T> getAll() {
		return getDao().findAll();
	}

	public Page<T> getAll(Specification<T> spec, Pageable page) {
		return getDao().findAll(spec, page);
	}

	public void delete(int id) {
		delete(getById(id));
	}
	
	public void delete(T entity) {
		beforeDelete(entity);
		getDao().delete(entity);
	}

	public T getById(int id) {
		Optional<T> opt = getDao().findById(id);
			return opt.orElse(null);
	}

	public long count() {
		return getDao().count();
	}

	protected abstract Idao<T, Integer> getDao();

}
