package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = FxmlPathProperties.PROPERTY_PREFIX)
public class FxmlPathProperties {

	public static final String PROPERTY_PREFIX = "fx.controller";

	private Resource matiereEdit ;
	private Resource typeEdit ;
	private Resource tissageEdit ;

	private Resource root2;
	private Resource tissus2;
	private Resource tissuCard;
	private Resource tissuDetail;
	private Resource tissuEdit;
	private Resource projetList;
	private Resource projetCard;
	private Resource projetDetail;
	private Resource projetEdit;
	private Resource patronList;
	private Resource patronCard;
	private Resource patronDetail;
	private Resource patronEdit;
	private Resource listElement;
}
