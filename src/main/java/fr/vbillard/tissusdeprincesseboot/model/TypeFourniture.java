package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
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
	
	@Enumerated(EnumType.STRING)
	private Unite unitePrincipale;
	
	@Enumerated(EnumType.STRING)
	private Unite uniteSecondaire;



}
