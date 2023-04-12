package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractUsedEntity<T> extends AbstractEntity{
	
	public abstract AbstractRequis<T> getRequis();

}
