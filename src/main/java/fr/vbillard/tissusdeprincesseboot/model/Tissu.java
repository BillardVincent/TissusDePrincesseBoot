package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tissu extends AbstractEntity {

	@Override
	public String toString() {
		return "Tissu [description=" + description + ", matiere=" + matiere + "]";
	}

	protected int longueur;
	private int longueurDisponible;
	protected int laize;
	@ManyToOne
	protected Matiere matiere;
	@ManyToOne
	protected Tissage tissage;
	private String reference;
	private String description;
	@Enumerated(EnumType.STRING)
	private TypeTissuEnum typeTissu = TypeTissuEnum.NON_RENSEIGNE;
	private boolean chute;
	private int poids;
	@Enumerated(EnumType.STRING)
	private UnitePoids unitePoids = UnitePoids.NON_RENSEIGNE;
	private boolean decati;
	private String lieuAchat;

	@ColumnDefault("false")
	private boolean archived;

	public Tissu(int id, String reference, int longueur, int laize, String description, Matiere matiere,
			TypeTissuEnum typeTissu, int poids, UnitePoids unitePoids, boolean decati, String lieuAchat,
			Tissage tissage, boolean chute, boolean archived) {
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
		this.archived = false;
	}

}
