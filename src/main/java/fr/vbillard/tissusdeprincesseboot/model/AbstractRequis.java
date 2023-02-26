package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRequis<T> extends AbstractEntity{
	
	@ManyToOne
	protected Patron patron;

	public abstract List<? extends AbstractVariant<T>> getVariants();


}
