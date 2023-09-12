package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.beans.property.*;

public class FournitureRequiseDto extends AbstractRequisDto<FournitureRequise, Fourniture> {


	private IntegerProperty id;
	private StringProperty typeName;
	private FloatProperty quantiteDisponible;
	private FloatProperty quantite;
	private Unite unite;
	private FloatProperty quantiteSecondaireMin;
	private FloatProperty quantiteSecondaireMax;
	private Unite uniteSecondaire;
	private TypeFourniture type;

	public FournitureRequiseDto() {
		id = new SimpleIntegerProperty();
		typeName = new SimpleStringProperty();
		quantiteDisponible = new SimpleFloatProperty();
		quantite = new SimpleFloatProperty();
		quantiteSecondaireMin = new SimpleFloatProperty();
		quantiteSecondaireMax = new SimpleFloatProperty();
		typeName = new SimpleStringProperty();
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
		if (type != null){
			this.typeName.set(type.getValue());
		}
	}
	
	public TypeFourniture getType() {
		return type;
	}

	public Unite getUnite() {
		return unite;
	}


	public void setUnite(Unite unite) {
		this.unite = unite;
	}

	public float getQuantiteSecondaireMin() {
		return quantiteSecondaireMin.get();
	}
	public float getQuantiteSecondaireMax() {
		return quantiteSecondaireMax.get();
	}

	public FloatProperty getQuantiteSecondaireMinProperty() {
		return quantiteSecondaireMin;
	}
	public FloatProperty getQuantiteSecondaireMaxProperty() {
		return quantiteSecondaireMax;
	}

	public void setQuantiteSecondaireMax(float quantite) {
		this.quantiteSecondaireMax.set(quantite);
	}
	public void setQuantiteSecondaireMin(float quantite) {
		this.quantiteSecondaireMin.set(quantite);
	}

	public Unite getUniteSecondaire() {
		return uniteSecondaire;
	}

	public void setUniteSecondaire(Unite unite) {
		this.uniteSecondaire = unite;
	}

	@Override
	public String toString() {
		return type.getValue() + " - " + type.getIntitulePrincipale() + " : " + getQuantite() + " " + getUnite().getAbbreviation();
	}
}
