package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Matiere extends AbstractSimpleValueEntity {

	public Matiere(int id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public Matiere(String value) {
		this.value = value;
	}

}
