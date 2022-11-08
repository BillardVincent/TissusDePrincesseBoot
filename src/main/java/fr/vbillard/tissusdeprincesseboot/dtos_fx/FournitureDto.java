package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FournitureDto implements FxDto{

	private IntegerProperty id;
	  private StringProperty nom;
	  private FloatProperty quantite;
	  private StringProperty unite;
	  private FloatProperty quantiteSecondaire;
	  private StringProperty uniteSecondaire;
	  private StringProperty lieuAchat;
	  private StringProperty reference;
	  private StringProperty description;
	  private FloatProperty quantiteDisponible;
	  private StringProperty type;
	  private BooleanProperty archived;

	public FournitureDto() {
		this.id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.quantite = new SimpleFloatProperty();
		this.quantiteDisponible = new SimpleFloatProperty();
		this.description = new SimpleStringProperty();
		this.unite = new SimpleStringProperty();
		this.nom = new SimpleStringProperty();
		this.lieuAchat = new SimpleStringProperty();
		this.archived = new SimpleBooleanProperty();
		this.type = new SimpleStringProperty();
		this.quantiteSecondaire = new SimpleFloatProperty();
		this.uniteSecondaire = new SimpleStringProperty();
	}

	public int getId() {
		return id.get();
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getReference() {
		return reference.get();
	}

	public StringProperty getReferenceProperty() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference.set(reference);
	}

	public float getQuantiteDisponible() {
		return quantiteDisponible.get();
	}

	public FloatProperty getQuantiteDisponibleProperty() {
		return quantiteDisponible;
	}

	public void setQuantiteDisponible(float quantiteDisponible) {
		this.quantiteDisponible.set(quantiteDisponible);
	}

	public float getQuantite() {
		return quantite.get();
	}

	public FloatProperty getQuantiteProperty() {
		return quantite;
	}

	public void setQuantite(float quantite) {
		this.quantite.set(quantite);
	}

	
	public String getDescription() {
		return description.get();
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public String getNom() {
		return nom.get();
	}

	public StringProperty getNomProperty() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public String getType() {
		return type.get();
	}

	public StringProperty getTypeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getUnite() {
		return unite.get();
	}

	public StringProperty getUniteProperty() {
		return unite;
	}

	public void setUnite(Unite unite) {
		if (unite == null) {
			unite = Unite.NON_RENSEIGNE;
		}

		this.unite.set(unite.getLabel());
	}
	
	public float getQuantiteSecondaire() {
		return quantiteSecondaire.get();
	}

	public FloatProperty getQuantiteSecondaireProperty() {
		return quantiteSecondaire;
	}

	public void setQuantiteSecondaire(float quantite) {
		this.quantiteSecondaire.set(quantite);
	}
	
	public String getUniteSecondaire() {
		return uniteSecondaire.get();
	}

	public StringProperty getUniteSecondaireProperty() {
		return uniteSecondaire;
	}

	public void setUniteSecondaire(Unite unite) {
		if (unite == null) {
			unite = Unite.NON_RENSEIGNE;
		}
		this.uniteSecondaire.set(unite.getLabel());
	}
	
	public StringProperty getLieuAchatProperty() {
		return lieuAchat;
	}

	public String getLieuAchat() {
		return lieuAchat.get();
	}

	public void setLieuAchat(String lieuAchat) {
		this.lieuAchat.set(lieuAchat);
	}

	public BooleanProperty getArchivedProperty() {
		return archived;
	}

	public boolean isArchived() {
		return archived.get();
	}

	public void setArchived(boolean archived) {
		this.archived.set(archived);
	}

}
