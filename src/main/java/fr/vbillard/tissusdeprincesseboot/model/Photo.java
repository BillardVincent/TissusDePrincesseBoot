package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
