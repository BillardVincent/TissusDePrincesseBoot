package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;

public abstract class AbstractRequisDto<T extends AbstractRequis<U>, U> implements FxDto<T> {
	
	protected ListProperty<String> variants;

	
	public List<String> getVariant() {
		return variants.get();
	}
	
	public ListProperty<String> getVariantProperty() {
		return variants;
	}
	
	public void setVariant(List<AbstractVariantDto> variants) {
		this.variants.set(FXCollections.observableArrayList(variants.stream().map(v -> v.toString()).collect(Collectors.toList())));
	}
	
}
