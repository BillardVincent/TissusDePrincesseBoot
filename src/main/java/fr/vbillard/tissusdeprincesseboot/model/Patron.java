package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

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

	@ColumnDefault("false")
	private boolean archived;
	
	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	@OneToMany(mappedBy = "patron", fetch = FetchType.EAGER)
	private List<PatronVersion> versions;
	
	@PrePersist
	private void setSupportType() {
		if(supportType == null) {
			supportType = SupportTypeEnum.NON_RENSEIGNE;
		}
	}

}
