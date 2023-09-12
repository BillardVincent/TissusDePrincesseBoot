package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjetDto implements FxDto<Projet> {

	@Autowired
	private Constants constants;

	private IntegerProperty id;

	private StringProperty description;

	private ObjectProperty<PatronVersionDto> patronVersion;

	private MapProperty<TissuRequisDto, List<Integer>> tissuUsed;

	private MapProperty<FournitureRequiseDto, List<Integer>> fournitureUsed;

	private StringProperty status;


	public ProjetDto() {
		this.id = new SimpleIntegerProperty();
		this.description = new SimpleStringProperty();

		this.status = new SimpleStringProperty();
		this.patronVersion = new SimpleObjectProperty<>();
		setPatronVersion(new PatronVersionDto());

		this.tissuUsed = new SimpleMapProperty<>();
		setTissuUsed(new HashMap<>());

		this.fournitureUsed = new SimpleMapProperty<>();
		setFournitureUsed(new HashMap<>());

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

	public String getDescription() {
		return description.get();
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setProjectStatus(ProjectStatus status) {
		if (status == null)
			this.status.set(constants.getNonEnregistre());
		else
			this.status.set(status.label);
	}

	public void setProjectStatus(String label) {
		this.status.set(label);
	}

	public String getProjectStatus() {
		return status.get();
	}

	public StringProperty getProjectStatusProperty() {
		return status;
	}

	public PatronVersionDto getPatronVersion() {
		return patronVersion.get();
	}

	public ObjectProperty<PatronVersionDto> getPatronProperty() {
		return patronVersion;
	}

	public void setPatronVersion(PatronVersionDto patronVersion) {
		this.patronVersion.set(patronVersion);
	}

	public Map<TissuRequisDto, List<Integer>> getTissuUsed() {
		return tissuUsed.get();
	}

	public MapProperty<TissuRequisDto, List<Integer>> getTissuUsedProperty() {
		return tissuUsed;
	}

	public void setTissuUsed(Map<TissuRequisDto, List<Integer>> tissuUsed) {
		this.tissuUsed.set(FXCollections.observableMap(tissuUsed));
	}

	public Map<FournitureRequiseDto, List<Integer>> getFournitureUsed() {
		return fournitureUsed.get();
	}

	public MapProperty<FournitureRequiseDto, List<Integer>> getFournitureUsedProperty() {
		return fournitureUsed;
	}

	public void setFournitureUsed(Map<FournitureRequiseDto, List<Integer>> fournitureUsed) {
		this.fournitureUsed.set(FXCollections.observableMap(fournitureUsed));
	}

}
