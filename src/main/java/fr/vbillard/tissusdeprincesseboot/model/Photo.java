package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Photo extends AbstractEntity{
	
	private String nom;
	
	@Enumerated(EnumType.STRING)
	private ImageFormat format;
	
	@Lob
	private byte[] data;
	
	@OneToOne
	private Tissu tissu;
	
	@OneToOne
	private Patron patron;
	
	@OneToOne
	private Fourniture fourniture;

	
}
