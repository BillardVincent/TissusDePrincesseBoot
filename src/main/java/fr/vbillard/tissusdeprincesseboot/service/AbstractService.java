package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractEntity> {

	/**
	 * methode to override if an action is required during a saveOrUpdate() operation
	 * @param entity AbstractEntity to save or update
	 */
	protected void beforeSaveOrUpdate(T entity){
	}

	/**
	 * methode to override if an action is required during a delete() operation
	 * @param entity AbstractEntity to delete
	 */
	protected void beforeDelete(T entity){
	}

	/**
	 * Do not Override this method. Use beforeSaveOrUpdate()
	 * Can't be final because of @Transactional
	 */
	@Transactional
	public T saveOrUpdate(T entity) {
		beforeSaveOrUpdate(entity);
		return getDao().save(entity);
	}

	public final List<T> getAll() {
		return getDao().findAll();
	}

	public final Page<T> getAll(Specification<T> spec, Pageable page) {
		return getDao().findAll(spec, page);
	}

	/**
	 * Do not Override this method. Use beforeDelete()
	 * Can't be final because of @Transactional
	 */
	@Transactional
	public void delete(int id) {
		delete(getById(id));
	}

	/**
	 * Do not Override this method. Use beforeDelete()
	 * Can't be final because of @Transactional
	 */
	@Transactional
	public void delete(T entity) {
		beforeDelete(entity);
		getDao().delete(entity);
	}

	public final T getById(int id) {
		Optional<T> opt = getDao().findById(id);
			return opt.orElse(null);
	}

	public final long count() {
		return getDao().count();
	}

	public abstract Idao<T, Integer> getDao();

}
