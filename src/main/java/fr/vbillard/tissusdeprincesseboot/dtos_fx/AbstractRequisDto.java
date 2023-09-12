package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import javafx.beans.property.ListProperty;

import java.util.List;

public abstract class AbstractRequisDto<T extends AbstractRequis<U>, U> implements FxDto<T> {
	
	protected ListProperty<String> variants;

	
	public List<String> getVariant() {
		return variants.get();
	}
	
	public ListProperty<String> getVariantProperty() {
		return variants;
	}

	
}
