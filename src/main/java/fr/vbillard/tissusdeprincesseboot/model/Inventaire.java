package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class Inventaire extends AbstractEntity{
	
	@ManyToOne
	Projet projet;
	
	@ManyToMany(fetch = FetchType.EAGER)
	List<TissuUsed> tissus;

}
