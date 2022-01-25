package fr.vbillard.tissusdeprincesseboot.dtosFx;

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
