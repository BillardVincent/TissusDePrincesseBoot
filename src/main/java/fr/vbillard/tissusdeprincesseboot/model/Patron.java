package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron extends AbstractEntity {

	private String reference;
	private String marque;
	private String modele;
	private String typeVetement;
	private String description;

	@OneToMany
	private List<TissuRequis> tissuRequis;

	@OneToMany
	private List<FournitureRequise> fournituresRequises;

}
