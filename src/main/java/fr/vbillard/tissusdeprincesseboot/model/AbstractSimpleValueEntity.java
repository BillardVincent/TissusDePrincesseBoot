package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSimpleValueEntity extends AbstractEntity {

	protected String value;

	@Override
	public String toString() {
		return value;
	}

}
