package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;

import java.util.List;


public abstract class AbstractRequisService<T extends AbstractRequis<U>, U extends AbstractEntity,
    V extends FxDto<T>> extends AbstractDtoService<T, V>{

  protected MapperService mapper;
  protected PatronVersionDao patronVersionDao;

  public abstract List<T> getAllByVersionId(int id);

  public abstract void delete(V requis);

  public abstract T createNewForPatron(int patronId);
}
