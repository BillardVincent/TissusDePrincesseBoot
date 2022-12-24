package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;


public abstract class AbstractRequisService<T extends AbstractRequis<U>, U extends AbstractEntity,
    V extends FxDto<T>> extends AbstractDtoService<T, V>{

  MapperService mapper;

  public V createOrUpdate(V tissu, PatronDto patron) {
    T t = convert(tissu);
    t.setPatron(mapper.map(patron));
    return convert(saveOrUpdate(t));
  }
  public abstract List<T> getAllRequisByPatron(int id);

  public abstract void delete(V requis);

}
