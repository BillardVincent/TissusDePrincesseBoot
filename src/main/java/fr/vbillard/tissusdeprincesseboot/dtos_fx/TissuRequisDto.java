package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class TissuRequisDto extends AbstractRequisDto<TissuRequis, Tissu>{

	private IntegerProperty id;
	private IntegerProperty longueur;
	private IntegerProperty laize;
	private StringProperty gammePoids;
	private IntegerProperty patronId;

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
