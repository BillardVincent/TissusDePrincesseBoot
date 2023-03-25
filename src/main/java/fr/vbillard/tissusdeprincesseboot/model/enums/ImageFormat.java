package fr.vbillard.tissusdeprincesseboot.model.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public enum ImageFormat {

	JPEG("JPEG", "*.jpeg"), JPG("JPG", "*.jpg"), GIF("GIF", "*.gif"), PNG("PNG", "*.png"), TIFF("TIFF", "*.tiff"),
	WEBP ("WEBP", "*.webp");

	private final String value;
	private final String extension;

	ImageFormat(String value, String extension) {
		this.value = value;
		this.extension = extension;
	}

	public static List<String> extensions() {
		List<String> list = new ArrayList();
		for (ImageFormat e : ImageFormat.values()) {
			list.add(e.extension);
		}
		return list;
	}

}
