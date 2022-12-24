package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FournitureRequiseDto implements FxDto<FournitureRequise> {


	private IntegerProperty id;
	private StringProperty typeName;
	private FloatProperty quantiteDisponible;
	private FloatProperty quantite;
	private StringProperty unite;
	private FloatProperty quantiteSecondaire;
	private StringProperty uniteSecondaire;
	private TypeFourniture type;

	public FournitureRequiseDto() {
		id = new SimpleIntegerProperty();
		typeName = new SimpleStringProperty();
		quantiteDisponible = new SimpleFloatProperty();
		quantite = new SimpleFloatProperty();
		unite = new SimpleStringProperty();
		quantiteSecondaire = new SimpleFloatProperty();
		uniteSecondaire = new SimpleStringProperty();
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

}
