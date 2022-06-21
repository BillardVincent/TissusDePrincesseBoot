package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserPref extends AbstractEntity {

	@Column(columnDefinition = "integer default 200")
	private int minPoidsMoyen;
	@Column(columnDefinition = "integer default 340")
	private int maxPoidsMoyen;
	@Column(columnDefinition = "float default 0.20")
	private float poidsMargePercent;
	@Column(columnDefinition = "float default 0.10")
	private float longueurMargePercent;

}
