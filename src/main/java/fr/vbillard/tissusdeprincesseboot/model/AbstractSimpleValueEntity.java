package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSimpleValueEntity extends AbstractEntity {

	@Column(unique=true)
	protected String value;

	@Override
	public String toString() {
		return value;
	}

}
