package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import org.springframework.util.StringUtils;

public class TissuDto implements FxDto<Tissu> {

	@Override
	public String toString() {
		String unit = unitePoids.get().equals(UnitePoids.NON_RENSEIGNE.label) ? "" : unitePoids.get();
		return "tissu [ longueur=" + longueur.get() + "cm, laize=" + laize.get() + "cm, description="
				+ description.get() + ", type=" + typeTissu.get() + ", matiere=" + matiere.get() + ", tissage="
				+ tissage.get() + ", poids=" + poids.get() + unit + "]";
	}

	private IntegerProperty id;
	private StringProperty reference;
	private IntegerProperty longueur;
	private IntegerProperty longueurRestante;
	private IntegerProperty laize;
	private StringProperty description;
	private StringProperty matiere;
	private StringProperty typeTissu;
	private StringProperty unitePoids;
	private IntegerProperty poids;
	private StringProperty lieuAchat;
	private BooleanProperty decati;
	private StringProperty tissage;
	private BooleanProperty chute;
	private BooleanProperty archived;
	private ObjectProperty<Color> color;
	private ObjectProperty<Rangement> rangement;
	public int colorId;

	public TissuDto() {
		this.id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.longueur = new SimpleIntegerProperty();
		this.laize = new SimpleIntegerProperty();
		this.description = new SimpleStringProperty();
		this.matiere = new SimpleStringProperty();
		this.typeTissu = new SimpleStringProperty();
		this.laize = new SimpleIntegerProperty();
		this.poids = new SimpleIntegerProperty();
		this.lieuAchat = new SimpleStringProperty();
		this.decati = new SimpleBooleanProperty();
		this.unitePoids = new SimpleStringProperty();
		this.tissage = new SimpleStringProperty();
		this.chute = new SimpleBooleanProperty();
		this.archived = new SimpleBooleanProperty();
		this.longueurRestante = new SimpleIntegerProperty();
		this.color = new SimpleObjectProperty<>();
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

	public int getLongueurRestante() {
		return longueurRestante.get();
	}

	public IntegerProperty getLongueurRestanteProperty() {
		return longueurRestante;
	}

	public void setLongueurRestante(int longueurRestante) {
		this.longueurRestante.set(longueurRestante);
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

	public String getDescription() {
		return description.get();
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
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

	public int getPoids() {
		return poids.get();
	}

	public IntegerProperty getPoidseProperty() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids.set(poids);
	}

	public void setUnitePoids(UnitePoids unitePoids) {
		if (unitePoids == null) {
			unitePoids = UnitePoids.NON_RENSEIGNE;
		}
		this.unitePoids.set(unitePoids.label);
	}

	public void setUnitePoids(String label) {
		if (!StringUtils.hasLength(label)) {
			label = UnitePoids.NON_RENSEIGNE.getLabel();
		}
		this.unitePoids.set(label);
	}

	public String getUnitePoids() {
		return unitePoids.get();
	}

	public StringProperty getUnitePoidsProperty() {
		return unitePoids;
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

	public BooleanProperty getDecatiProperty() {
		return decati;
	}

	public boolean isDecati() {
		return decati.get();
	}

	public void setDecati(boolean decati) {
		this.decati.set(decati);
	}

	public void setChute(boolean chute) {
		this.chute.set(chute);
	}

	public BooleanProperty getChuteProperty() {
		return chute;
	}

	public boolean isChute() {
		return chute.get();
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

	public boolean isArchived() {
		return archived.get();
	}

	public BooleanProperty archivedProperty() {
		return archived;
	}

	public void setArchived(boolean archived){
		this.archived.set(archived);
	}

	public Color getColor() {
		return color.get();
	}

	public ObjectProperty<Color> getColorProperty() {
		return color;
	}

	public void setColor(Color color) {
		this.color.set(color);
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