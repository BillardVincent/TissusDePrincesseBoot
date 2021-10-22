package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Preference {

	private String dataBasePath;
	private String pictureLastUploadPath;
	private boolean isFirstLaunch;
	
}
