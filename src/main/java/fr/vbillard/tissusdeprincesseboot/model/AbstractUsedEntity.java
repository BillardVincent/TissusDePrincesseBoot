package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractUsedEntity<T> extends AbstractEntity{
	
	public abstract AbstractRequis<T> getRequis();

}
