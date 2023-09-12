package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRequis<T> extends AbstractEntity{
	
	@ManyToOne
	@Cascade(CascadeType.PERSIST)
	protected PatronVersion version;


}
