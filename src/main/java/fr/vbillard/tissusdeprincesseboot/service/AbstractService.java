package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public abstract class AbstractService<T extends AbstractEntity> {

	protected abstract void beforeSaveOrUpdate(T entity);

	public T saveOrUpdate(T entity) {
		beforeSaveOrUpdate(entity);
		return (T) getDao().save(entity);
	}

	public List<T> getAll() {
		return getDao().findAll();
	}

	public Page<T> getAll(Specification<T> spec, Pageable page) {
		return getDao().findAll(spec, page);
	}

	public void delete(T entity) {
		getDao().delete(entity);
	}

	public T getById(int id) {
		return (T) getDao().findById(id).get();
	}

	public long count() {
		return getDao().count();
	}

	protected abstract Idao<T, Integer> getDao();

}
