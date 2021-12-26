package fr.vbillard.tissusdeprincesseboot.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
	
	
	
	
}
