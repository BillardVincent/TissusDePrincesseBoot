package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public abstract class AbstractDtoService <T extends AbstractEntity, U extends FxDto<T>> extends AbstractService<T>{
  abstract public T convert(U dto);
  abstract public U convert(T entity);

  @Transactional
  public U saveOrUpdate(U dto){
    return convert(saveOrUpdate(convert(dto)));
  }

  public List<T> convertToEntiy(List<U> dtos){
    return dtos.stream().map(this::convert).collect(Collectors.toList());
  }

  public List<U> convertToDto(List<T> entities){
    return entities.stream().map(this::convert).collect(Collectors.toList());
  }
  
  public List<U> convertToDto(Page<T> entities){
	    return entities.stream().map(this::convert).collect(Collectors.toList());
	  }

    @Transactional
  public  U getDtoById(int id){
    return convert(getById(id));
  }


}
