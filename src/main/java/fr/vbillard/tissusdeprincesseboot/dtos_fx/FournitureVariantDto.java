package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FournitureVariantDto implements FxDto {

	private IntegerProperty id;
	private IntegerProperty fournitureRequiseId;
	private StringProperty intituleSecondaire;
	private FloatProperty quantiteSecondaireMin;
	private FloatProperty quantiteSecondaireMax;
	private StringProperty uniteSecondaire;
	private StringProperty typeName;
	private TypeFourniture type;

	public FournitureVariantDto() {
		this.id = new SimpleIntegerProperty();
		this.fournitureRequiseId = new SimpleIntegerProperty();
		this.typeName = new SimpleStringProperty();
		this.quantiteSecondaireMin = new SimpleFloatProperty();
		this.quantiteSecondaireMax = new SimpleFloatProperty();
		this.uniteSecondaire = new SimpleStringProperty();
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

	public int getFournitureRequiseId() {
		return fournitureRequiseId.get();
	}

	public IntegerProperty getFournitureRequiseIdProperty() {
		return fournitureRequiseId;
	}

	public void setFournitureRequiseId(int id) {
		this.fournitureRequiseId.set(id);
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

	public float getQuantiteSecondaireMin() {
		return quantiteSecondaireMin.get();
	}

	public FloatProperty getQuantiteSecondaireMinProperty() {
		return quantiteSecondaireMin;
	}

	public void setQuantiteSecondaireMin(float quantite) {
		this.quantiteSecondaireMin.set(quantite);
	}

	public float getQuantiteSecondaireMax() {
		return quantiteSecondaireMax.get();
	}

	public FloatProperty getQuantiteSecondaireMaxProperty() {
		return quantiteSecondaireMax;
	}

	public void setQuantiteSecondaireMax(float quantite) {
		this.quantiteSecondaireMax.set(quantite);
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
	public StringProperty getIntituleSecondaireProperty() {
		return intituleSecondaire;
	}

	public String getIntituleSecondaire() {
		return intituleSecondaire.get();
	}

	public void setIntituleSecondaire(String intituleSecondaire) {
		this.intituleSecondaire.set(intituleSecondaire);
	}
}
