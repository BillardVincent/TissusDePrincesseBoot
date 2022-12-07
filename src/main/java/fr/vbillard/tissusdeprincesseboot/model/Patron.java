package fr.vbillard.tissusdeprincesseboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.aspectj.lang.annotation.Before;

import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron extends AbstractEntity {

	private String reference;
	private String marque;
	private String modele;
	private String typeVetement;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	@OneToMany
	private List<TissuRequis> tissuRequis;

	@OneToMany
	private List<FournitureRequise> fournituresRequises;
	
	@PrePersist
	private void setSupportType() {
		if(supportType == null) {
			supportType = SupportTypeEnum.NON_RENSEIGNE;
		}
	}

}
