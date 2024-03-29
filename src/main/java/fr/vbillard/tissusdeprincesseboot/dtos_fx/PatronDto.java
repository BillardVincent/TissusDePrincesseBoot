package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import javafx.beans.property.*;

public class PatronDto implements FxDto<Patron>{

	private IntegerProperty id;
	private StringProperty reference;
	private StringProperty marque;
	private StringProperty modele;
	private StringProperty typeVetement;
	private StringProperty description;
	private BooleanProperty archived;
	private StringProperty typeSupport;
	private ObjectProperty<Rangement> rangement;

	
	public PatronDto() {
		id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.marque = new SimpleStringProperty();
		this.modele = new SimpleStringProperty();
		this.typeVetement = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		typeSupport = new SimpleStringProperty();
		this.archived = new SimpleBooleanProperty();
		this.rangement = new SimpleObjectProperty<>();

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
	
	public String getMarque() {
		return marque.get();
	}
	public StringProperty getMarqueProperty() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque.set(marque);
	}
	
	public String getModele() {
		return modele.get();
	}
	public StringProperty getModeleProperty() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele.set(modele);
	}
	
	public String getTypeVetement() {
		return typeVetement.get();
	}
	public StringProperty getTypeVetementProperty() {
		return typeVetement;
	}

	public void setTypeVetement(String typeVetement) {
		this.typeVetement.set(typeVetement);
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



	public void setTypeSupport(SupportTypeEnum typeSupport) {
		if (typeSupport == null)
			this.typeSupport.set(SupportTypeEnum.NON_RENSEIGNE.label);
		else
			this.typeSupport.set(typeSupport.label);
	}

	public void setTypeSupport(String label) {
		this.typeSupport.set(label);
	}

	public String getTypeSupport() {
		return typeSupport.get();
	}

	public StringProperty getTypeSupportProperty() {
		return typeSupport;
	}
	
	@Override
	public String toString() {
		return " " + modele.getValue()+" ("+typeVetement.getValue() + ") de la marque "+marque.getValue()+" (ref. " + reference.getValue() + ").";
	}

	public boolean isArchived() {
		return archived.get();
	}

	public BooleanProperty archivedProperty() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived.set(archived);
	}

	public Rangement getRangement() {
		return rangement.get();
	}

	public ObjectProperty<Rangement> getRangementProperty() {
		return rangement;
	}

	public void setRangement(Rangement rangement) {
		this.rangement.set(rangement);
	}
}
