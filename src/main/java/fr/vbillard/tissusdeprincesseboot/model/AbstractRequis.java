package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRequis<T> extends AbstractEntity{
	
	@ManyToOne
	@Cascade(CascadeType.PERSIST)
	protected Patron patron;

	public abstract List<? extends AbstractVariant<T>> getVariants();


}
