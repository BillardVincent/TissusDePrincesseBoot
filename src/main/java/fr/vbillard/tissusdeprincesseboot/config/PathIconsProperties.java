package fr.vbillard.tissusdeprincesseboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = PathIconsProperties.PROPERTY_PREFIX)
public class PathIconsProperties {

	public static final String PROPERTY_PREFIX = "fx.path.icon";

	private Resource washingMachine;
}
