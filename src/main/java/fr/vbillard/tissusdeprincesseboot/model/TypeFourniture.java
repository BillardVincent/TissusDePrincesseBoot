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
public class TypeFourniture extends AbstractSimpleValueEntity {

	@OneToMany
	protected List<Fourniture> fournitures;

	public TypeFourniture(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public TypeFourniture(String value) {
		this.value = value;
	}

}
