package fr.vbillard.tissusdeprincesseboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = PathImgProperties.PROPERTY_PREFIX)
public class PathImgProperties {

	public static final String PROPERTY_PREFIX = "fx.path.img";

	private Resource imageDefault ;
	
	private Resource appIcon;
}
