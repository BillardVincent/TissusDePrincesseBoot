package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FournitureDto implements FxDto<Fourniture> {

	private IntegerProperty id;
	private StringProperty nom;
	private StringProperty intituleDimension;
	private FloatProperty quantite;
	private StringProperty unite;
	private StringProperty uniteShort;
	private StringProperty intituleSecondaire;
	private FloatProperty quantiteSec;
	private StringProperty uniteSecondaire;
	private StringProperty uniteSecondaireShort;
	private StringProperty lieuAchat;
	private StringProperty reference;
	private StringProperty description;
	private FloatProperty quantiteDisponible;
	private StringProperty typeName;
	private BooleanProperty archived;
	private TypeFourniture type;

	public FournitureDto() {
		this.id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.quantite = new SimpleFloatProperty();
		this.quantiteDisponible = new SimpleFloatProperty();
		this.description = new SimpleStringProperty();
		this.unite = new SimpleStringProperty();
		this.uniteShort = new SimpleStringProperty();
		this.nom = new SimpleStringProperty();
		this.lieuAchat = new SimpleStringProperty();
		this.archived = new SimpleBooleanProperty();
		this.typeName = new SimpleStringProperty();
		this.quantiteSec = new SimpleFloatProperty();
		this.uniteSecondaire = new SimpleStringProperty();
		this.uniteSecondaireShort = new SimpleStringProperty();
		this.intituleDimension = new SimpleStringProperty();
		this.intituleSecondaire = new SimpleStringProperty();
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

	public String getTypeName() {
		return typeName.get();
	}

	public StringProperty getTypeNameProperty() {
		return typeName;
	}

	public void setType(TypeFourniture type) {
		this.type = type;
		this.typeName.set(type.getValue());
	}
	
	public TypeFourniture getType() {
		return type;
	}

	public String getUnite() {
		return unite.get();
	}
	public String getUniteShort() {
		return uniteShort.get();
	}

	public StringProperty getUniteProperty() {
		return unite;
	}
	public StringProperty getUniteShortProperty() {
		return uniteShort;
	}

	public void setUnite(Unite unite) {
		if (unite == null) {
			unite = Unite.NON_RENSEIGNE;
		}

		this.uniteShort.set(unite.getAbbreviation());
		this.unite.set(unite.getLabel());
	}

	public float getQuantiteSec() {
		return quantiteSec.get();
	}

	public FloatProperty getQuantiteSecondaireProperty() {
		return quantiteSec;
	}

	public void setQuantiteSec(float quantite) {
		this.quantiteSec.set(quantite);
	}

	public String getUniteSecondaire() {
		return uniteSecondaire.get();
	}
	public String getUniteSecondaireShort() {
		return uniteSecondaireShort.get();
	}

	public StringProperty getUniteSecondaireProperty() {
		return uniteSecondaire;
	}
	public StringProperty getUniteShortSecondaireProperty() {
		return uniteSecondaireShort;
	}

	public void setUniteSecondaire(Unite unite) {
		if (unite == null) {
			unite = Unite.NON_RENSEIGNE;
		}
		this.uniteSecondaireShort.set(unite.getAbbreviation());
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

	public StringProperty getIntituleDimensionProperty() {
		return intituleDimension;
	}

	public String getIntituleDimension() {
		return intituleDimension.get();
	}

	public void setIntituleDimension(String intituleDimension) {
		this.intituleDimension.set(intituleDimension);
	}

	public StringProperty getIntituleSecondaireProperty() {
		return intituleSecondaire;
	}

	public String getIntituleSecondaire() {
		return intituleSecondaire.get();
	}

	public void setIntituleSecondaire(String intituleSecondaire) {
		this.intituleSecondaire.set(intituleSecondaire);
	}

	@Override
	public String toString() {
		return "Fourniture - "+typeName.get()+" [ nom : " + nom.get() + ", " + intituleDimension.get() + " : " + quantite.get() + unite.get() + ", " +
				intituleSecondaire.get() + " : " + quantiteSec.get() + uniteSecondaire.get() + ']';
	}
}
