package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tissu extends AbstractEntity{

	@Override
	public String toString() {
		return "Tissu [description=" + description + ", matiere=" + matiere + "]";
	}

	protected int longueur;
	protected int laize;
	@ManyToOne
	protected Matiere matiere;
	@ManyToOne
	protected Tissage tissage;
	private String reference;
	private String description;
	@ManyToOne
	private TypeTissu typeTissu;
	private boolean chute;
	private int poids;
	@Enumerated(EnumType.STRING)
	private UnitePoids unitePoids;
	private boolean decati;
	private String lieuAchat;
	private Boolean archived;
	
	
	public Tissu(int id, String reference , int longueur, int laize, String description, Matiere matiere, TypeTissu typeTissu, int poids, UnitePoids unitePoids, boolean decati, String lieuAchat, Tissage tissage, boolean chute) {
		super();
		this.id = id;
		this.reference = reference;
		this.longueur = longueur;
		this.laize = laize;
		this.description = description;
		this.matiere = matiere;
		this.typeTissu = typeTissu;
		this.unitePoids = unitePoids;
		this.poids = poids;
		this.decati = decati;
		this.lieuAchat = lieuAchat;
		this.chute = chute;
		this.tissage = tissage;
	}
	
	
	
	
	
}
