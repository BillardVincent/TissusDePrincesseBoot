package fr.vbillard.tissusdeprincesseboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = WindowNameProperties.PROPERTY_PREFIX)
public class WindowNameProperties {

	public static final String PROPERTY_PREFIX = "fx.window.title";

	private Resource main;
	private Resource longueur;
}
