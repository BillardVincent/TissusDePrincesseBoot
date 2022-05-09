package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public abstract class AbstractService<T extends AbstractEntity> {

	public T saveOrUpdate(T entity) {
		return (T) getDao().save(entity);
	}

	public List<T> getAll() {
		return getDao().findAll();
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

	protected abstract JpaRepository<T, Integer> getDao();

}
