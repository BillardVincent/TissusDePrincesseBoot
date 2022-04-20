package fr.vbillard.tissusdeprincesseboot.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:constantesMetier.properties")
@ConfigurationProperties(prefix = ConstantesMetier.PROPERTY_PREFIX)
public class ConstantesMetier {

	public static final String PROPERTY_PREFIX = "poids";

	private int maxPoidsMoyen;
	private int minPoidsMoyen;
	private float margePoidsErreur;

}
