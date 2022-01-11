package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TypeTissu extends AbstractSimpleValueEntity{

	
	public TypeTissu(int id, String type) {
		this.id = id;
		this.value = type;
	}
	
	public TypeTissu(String type) {
		this.value = type;
	}


}
