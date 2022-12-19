package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractVariant;

public abstract class AbstractVariantDto<T extends AbstractVariant<U>, U extends AbstractEntity> implements FxDto<T> {

}
