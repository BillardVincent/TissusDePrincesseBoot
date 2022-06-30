package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import org.springframework.util.StringUtils;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TissuVariantDto implements FxDto {

	private IntegerProperty id;
	private IntegerProperty tissuRequisId;
	private StringProperty typeTissu;
	private StringProperty matiere;
	private StringProperty tissage;

	public TissuVariantDto() {
		this.id = new SimpleIntegerProperty();
		this.tissuRequisId = new SimpleIntegerProperty();
		this.matiere = new SimpleStringProperty();
		this.typeTissu = new SimpleStringProperty();
		this.tissage = new SimpleStringProperty();
	}

	@Override
	public String toString() {
		return getMatiere() + ", " + getTypeTissu() + ", " + getTissage();
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

	public int getTissuRequisId() {
		return tissuRequisId.get();
	}

	public IntegerProperty getTissuRequisIdProperty() {
		return tissuRequisId;
	}

	public void setTissuRequisId(int tissuRequisId) {
		this.tissuRequisId.set(tissuRequisId);
	}

	public void setTissuRequisId(TissuRequis tissuRequis) {
		this.tissuRequisId.set(tissuRequis.getId());
	}

	public String getMatiere() {
		return matiere.get();
	}

	public StringProperty getMatiereProperty() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere.set(matiere);
	}

	public String getTypeTissu() {
		return typeTissu.get();
	}

	public StringProperty getTypeTissuProperty() {
		return typeTissu;
	}

	public void setTypeTissu(TypeTissuEnum type) {
		if (type == null) {
			type = TypeTissuEnum.NON_RENSEIGNE;
		}

		this.typeTissu.set(type.label);
	}

	public void setTypeTissu(String type) {
		if (!StringUtils.hasLength(type)) {
			type = TypeTissuEnum.NON_RENSEIGNE.getLabel();
		}
		this.typeTissu.set(type);

	}

	public StringProperty getTissageProperty() {
		return tissage;
	}

	public String getTissage() {
		return tissage.get();
	}

	public void setTissage(String tissage) {
		this.tissage.set(tissage);
	}
}
