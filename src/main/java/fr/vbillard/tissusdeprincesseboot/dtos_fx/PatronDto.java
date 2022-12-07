package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import java.util.ArrayList;
import java.util.List;

import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PatronDto implements FxDto{

	
	private IntegerProperty id;
	private StringProperty reference;
	private StringProperty marque;
	private StringProperty modele;
	private StringProperty typeVetement;
	private StringProperty description;
	
	private StringProperty typeSupport;
	private ListProperty<TissuRequisDto> tissusRequis;
	private ListProperty<FournitureRequiseDto> fournituresRequises;
	
	public PatronDto() {
		id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.marque = new SimpleStringProperty();
		this.modele = new SimpleStringProperty();
		this.typeVetement = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		typeSupport = new SimpleStringProperty();
		this.tissusRequis = new SimpleListProperty<TissuRequisDto>();
		setTissusRequis(new ArrayList<TissuRequisDto>());
		this.fournituresRequises = new SimpleListProperty<FournitureRequiseDto>();
		setFournituresRequises(new ArrayList<FournitureRequiseDto>());
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
	
	
	public void setTissusRequis(List<TissuRequisDto> tissusRequis) {
		ObservableList obs = FXCollections.observableArrayList(tissusRequis);
		this.tissusRequis.set(obs);
	}
	
	public void setFournituresRequises(List<FournitureRequiseDto> fournituresRequises) {
		ObservableList obs = FXCollections.observableArrayList(fournituresRequises);
		this.fournituresRequises.set(obs);
	}
	public List<FournitureRequiseDto> getFournituresRequises() {
		return fournituresRequises.get();
	}
	public ListProperty<FournitureRequiseDto> getFournituresRequisesProperty() {
		return fournituresRequises;
	}

	@Override
	public String toString() {
		return " " + modele.getValue()+" ("+typeVetement.getValue() + ") de la marque "+marque.getValue()+" (ref. " + reference.getValue() + ").";
	}
}
