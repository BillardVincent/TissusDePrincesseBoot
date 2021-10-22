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
public class Tissage extends AbstractSimpleValueEntity {

	
	public Tissage(int id, String tissage) {
		this.id = id;
		this.value = tissage;
	}
	
	public Tissage(String tissage) {
		this.value = tissage;
	}


}
