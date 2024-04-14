package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;


public abstract class AbstractRequisService<T extends AbstractRequis<U>, U extends AbstractEntity,
    V extends FxDto<T>> extends AbstractDtoService<T, V>{

  protected MapperService mapper;
  @Lazy
  @Autowired
  protected PatronVersionService patronVersionService;

  protected AbstractRequisService(MapperService mapper){
	  this.mapper = mapper;
      }
  
  public abstract List<T> getAllByVersionId(int id);

  public abstract void delete(V requis);

  public abstract T createNewForPatron(int patronId);
}
