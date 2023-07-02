package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;


public abstract class AbstractRequisService<T extends AbstractRequis<U>, U extends AbstractEntity,
    V extends FxDto<T>> extends AbstractDtoService<T, V>{

  protected MapperService mapper;
  protected PatronVersionService patronVersionService;

  protected AbstractRequisService(MapperService mapper, PatronVersionService patronVersionService){
	  this.mapper = mapper;
    this.patronVersionService = patronVersionService;
  }

  @Transactional
  public V createOrUpdate(V tissu, PatronVersionDto versionDto) {
    T t = convert(tissu);
    PatronVersion p = patronVersionService.saveOrUpdate(mapper.map(versionDto));
    t.setVersion(p);
    return convert(saveOrUpdate(t));
  }
  
  public abstract List<T> getAllByVersionId(int id);

  public abstract void delete(V requis);

  public abstract T createNewForPatron(int patronId);
}
