package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Patron extends AbstractEntity{
	

	private String reference;
	private String marque;
	private String modele;
	private String typeVetement;
	private String description;
	
	@Transient
	private List<FounitureRequise> fournituresRequises;
	

}
