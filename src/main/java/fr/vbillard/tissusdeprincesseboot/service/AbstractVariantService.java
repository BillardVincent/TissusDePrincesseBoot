package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractVariant;

public abstract class AbstractVariantService<T extends AbstractVariant<U>, U, V extends FxDto<T>> extends AbstractDtoService<T
		, V>{

	public abstract List<T> getVariantByRequis(AbstractRequis<U> requis);

	public  List<V> getVariantDtoByRequis(AbstractRequis<U> requis){
		return convertToDto(getVariantByRequis(requis));
	}


}
