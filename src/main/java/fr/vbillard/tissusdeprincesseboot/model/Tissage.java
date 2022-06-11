package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tissage extends AbstractSimpleValueEntity {

	@OneToMany
	protected List<Tissu> tissu;

	public Tissage(int id, String tissage) {
		this.id = id;
		this.value = tissage;
	}

	public Tissage(String tissage) {
		this.value = tissage;
	}

}
