package fr.vbillard.tissusdeprincesseboot.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = PathProperties.PROPERTY_PREFIX)
public class PathProperties {

	public static final String PROPERTY_PREFIX = "fx.path";

	private Resource imageDefault ;
}
