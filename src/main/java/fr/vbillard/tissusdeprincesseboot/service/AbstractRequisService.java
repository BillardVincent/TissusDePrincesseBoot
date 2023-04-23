package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.Patron;


public abstract class AbstractRequisService<T extends AbstractRequis<U>, U extends AbstractEntity,
    V extends FxDto<T>> extends AbstractDtoService<T, V>{

  protected MapperService mapper;
  protected PatronService patronService;
  
  protected AbstractRequisService(MapperService mapper){
	  this.mapper = mapper;
  }

  @Transactional
  public V createOrUpdate(V tissu, PatronDto patron) {
    T t = convert(tissu);
    Patron p = patronService.saveOrUpdate(mapper.map(patron));
    t.setPatron(p);
    return convert(saveOrUpdate(t));
  }
  
  public abstract List<T> getAllRequisByPatron(int id);

  public abstract void delete(V requis);

}
