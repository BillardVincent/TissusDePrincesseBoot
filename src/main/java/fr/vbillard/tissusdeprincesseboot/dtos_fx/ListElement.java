package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import lombok.Data;

@Data
public class ListElement implements FxDto{
	
	private String className;
	private String reference;
	private int id;
	private String titre;
	private String description;
	private String dimensions;

}
