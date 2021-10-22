package fr.vbillard.tissusdeprincesseboot.dtosFx;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.NoArgsConstructor;

public class TissuRequisDto {

	private IntegerProperty id;
	private IntegerProperty longueur;
	private IntegerProperty laize;
	private StringProperty gammePoids;
	private IntegerProperty patronId;
	private ListProperty<String> variants;

	public TissuRequisDto(){
		id = new SimpleIntegerProperty(0);
		longueur = new SimpleIntegerProperty(0);
		laize = new SimpleIntegerProperty(0);
		gammePoids = new SimpleStringProperty("");
		patronId = new SimpleIntegerProperty(0);
		variants = new SimpleListProperty<String>();
	}
	
	@Override
	public String toString() {
		return "Tissu " + getGammePoids()+ " : " + getLongueur() +"cm x "+ getLaize() +"cm";
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
	
	public int getLongueur() {
		return longueur.get();
	}
	public IntegerProperty getLongueurProperty() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur.set(longueur);
	}

	public int getLaize() {
		return laize.get();
	}
	
	public IntegerProperty getLaizeProperty() {
		return laize;
	}

	public void setVariant(List<TissuVariantDto> variants) {
		this.variants.set(FXCollections.observableArrayList(variants.stream().map(v-> v.toString()).collect(Collectors.toList())));
	}
	
	public List<String> getVariant() {
		return variants.get();
	}
	
	public ListProperty<String> getVariantProperty() {
		return variants;
	}

	public void setLaize(int laize) {
		this.laize.set(laize);
	}
	public void setGammePoids(GammePoids gammePoids2) {
		this.gammePoids.set(gammePoids2 == null? GammePoids.NON_RENSEIGNE.label : gammePoids2.label);
	}
	public void setGammePoids(String label) {
		this.gammePoids.set(label);		
	}
	
	public String getGammePoids() {
		return gammePoids.get();
	}
	public StringProperty getGammePoidsProperty() {
		return gammePoids;
	}
	
	public int getPatronId() {
		return patronId.get();
	}
	
	public IntegerProperty getPatronIdProperty() {
		return patronId;
	}

	public void setPatronId(int patronId) {
		this.patronId.set(patronId);
	}
}
