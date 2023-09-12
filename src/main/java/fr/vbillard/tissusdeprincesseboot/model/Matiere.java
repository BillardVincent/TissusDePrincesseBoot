package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Matiere extends AbstractSimpleValueEntity {

	@OneToMany
	protected List<Tissu> tissu;

	public Matiere(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public Matiere(String value) {
		this.value = value;
	}

}
