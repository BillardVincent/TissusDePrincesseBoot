package fr.vbillard.tissusdeprincesseboot.config;

import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.ConfigurationConfigurer;

import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

@Component

public class ModelMapperConfig extends ConfigurationConfigurer {

	@Override
	public void configure(Configuration configuration) {
		configuration.setSkipNullEnabled(true);
		configuration.setMatchingStrategy(MatchingStrategies.STRICT);
	}

}
