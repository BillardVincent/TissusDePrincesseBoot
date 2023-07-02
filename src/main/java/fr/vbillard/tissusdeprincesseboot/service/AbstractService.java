package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public abstract class AbstractService<T extends AbstractEntity> {

	/**
	 * methode to override if an action is required during a saveOrUpdate() operation
	 * @param entity
	 */
	protected void beforeSaveOrUpdate(T entity){
	}

	/**
	 * methode to override if an action is required during a delete() operation
	 * @param entity
	 */
	protected void beforeDelete(T entity){
	}

	@Transactional
	/**
	 * Do not Override this method. Use beforeSaveOrUpdate()
	 */
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

	public final void delete(int id) {
		delete(getById(id));
	}
	
	public final void delete(T entity) {
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

	protected abstract Idao<T, Integer> getDao();

	/**
	 * Clone an entity, reset its it and save it
	 * @param entity
	 * @return
	 */
	public final T clone(T entity) {
		T clone = SerializationUtils.clone(entity);
		clone.setId(0);
		afterClone(clone);
		saveOrUpdate(clone);
 		return clone;
	}

	/**
	 * operations to performe after cloning but before a saveOrUpdate
	 * @param clone entity to clone
	 */
	protected void afterClone(T clone){

	}

	public final T clone( int id) {
		T template = getById(id);
		return clone(template);
	}


}
