package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractDtoService <T extends AbstractEntity, U extends FxDto<T>> extends AbstractService<T>{
  public abstract T convert(U dto);

  public abstract U convert(T entity);

  @Transactional
  public U saveOrUpdate(U dto){
    return convert(saveOrUpdate(convert(dto)));
  }

  public List<T> convertToEntiy(List<U> dtos){
    return dtos.stream().map(this::convert).toList();
  }

  public List<U> convertToDto(List<T> entities){
    return entities.stream().map(this::convert).toList();
  }
  
  public List<U> convertToDto(Page<T> entities){
	    return entities.stream().map(this::convert).toList();
	  }

    @Transactional
  public  U getDtoById(int id){
    return convert(getById(id));
  }

}
