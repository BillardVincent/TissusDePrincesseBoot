package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

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
