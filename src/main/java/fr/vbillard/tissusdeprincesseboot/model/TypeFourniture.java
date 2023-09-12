package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TypeFourniture extends AbstractSimpleValueEntity {
 
	@OneToMany(mappedBy = "type")
	protected List<Fourniture> fournitures;

	private String intitulePrincipale;
	
	@Enumerated(EnumType.STRING)
	private DimensionEnum dimensionPrincipale;

	@Enumerated(EnumType.STRING)
	private Unite unitePrincipaleConseillee;

	private String intituleSecondaire;

	@Enumerated(EnumType.STRING)
	private DimensionEnum dimensionSecondaire;

	@Enumerated(EnumType.STRING)
	private Unite uniteSecondaireConseillee;



}
