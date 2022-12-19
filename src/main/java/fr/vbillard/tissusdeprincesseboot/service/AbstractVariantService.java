package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.AbstractVariant;

public abstract class AbstractVariantService<T extends AbstractVariant<U>, U> extends AbstractService<T>{

	protected abstract List<T> getVariantByRequis(AbstractRequis<T> requis);

}
