package fr.vbillard.tissusdeprincesseboot.dtosFx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PatronDto {

	
	private IntegerProperty id;
	private StringProperty reference;
	private StringProperty marque;
	private StringProperty modele;
	private StringProperty typeVetement;
	private StringProperty description;

	private ListProperty<TissuRequisDto> tissusRequis;
	
	public PatronDto() {
		this.id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.marque = new SimpleStringProperty();
		this.modele = new SimpleStringProperty();
		this.typeVetement = new SimpleStringProperty();
		this.description = new SimpleStringProperty();

		this.tissusRequis = new SimpleListProperty<TissuRequisDto>();
		setTissusRequis(new ArrayList<TissuRequisDto>());
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

	public List<TissuRequisDto> getTissusRequis() {
		return tissusRequis.get();
	}
	public ListProperty<TissuRequisDto> getTissusRequisProperty() {
		return tissusRequis;
	}

	public void setTissusRequis(List<TissuRequisDto> tissusRequis) {
		ObservableList obs = FXCollections.observableArrayList(tissusRequis);
		this.tissusRequis.set(obs);
	}

	@Override
	public String toString() {
		return " " + modele.getValue()+" ("+typeVetement.getValue() + ") de la marque "+marque.getValue()+" (ref. " + reference.getValue() + ").";
	}
}
