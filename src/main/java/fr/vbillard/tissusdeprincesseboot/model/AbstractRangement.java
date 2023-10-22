package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRangement extends AbstractEntity{

	protected String nom;

	protected int rang;

	public abstract List<Rangement> getSubdivision();

	public abstract void setSubdivision(List<Rangement> rangements);


}
