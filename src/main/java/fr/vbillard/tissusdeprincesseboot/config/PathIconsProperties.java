package fr.vbillard.tissusdeprincesseboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:iconeSvgPath.properties")
@ConfigurationProperties(prefix = PathIconsProperties.PROPERTY_PREFIX)
public class PathIconsProperties {

	public static final String PROPERTY_PREFIX = "svg";

	private Resource washingMachine;
	private double washingMachineSize;
	private Resource noWashingMachine;
	private double noWashingMachineSize;
	private Resource chaineEtTrame;
	private double chaineEtTrameSize;
	private Resource nonTisse;
	private double nonTisseSize;
	private Resource maille;
	private double mailleSize;
	private Resource multiligne;
	private double multiligneSize;
	private Resource textBoxCheck;
	private double textBoxCheckSize;
	private Resource textBoxRemove;
	private double textBoxRemoveSize;

}
