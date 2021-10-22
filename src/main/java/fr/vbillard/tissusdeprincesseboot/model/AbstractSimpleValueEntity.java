package fr.vbillard.tissusdeprincesseboot.model;


import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSimpleValueEntity extends AbstractEntity{

	protected String value;

}
