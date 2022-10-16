package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inventaire extends AbstractEntity{
	
	@ManyToOne
	Projet projet;
	
	@ManyToMany(fetch = FetchType.EAGER)
	List<TissuUsed> tissus;

}
