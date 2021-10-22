package fr.vbillard.tissusdeprincesseboot.model.enums;

import lombok.Getter;

@Getter
public enum ImageFormat {

	JPEG ("JPEG", "*.jpeg"),
	JPG("JPG","*.jpg"),
	GIF("GIF", "*.gif"),
	TIFF("TIFF", "*.tiff");
	
	private String value;
	private String extension;
	
	ImageFormat(String value, String extension){
		this.value = value;
		this.extension = extension;
	}
	
}
