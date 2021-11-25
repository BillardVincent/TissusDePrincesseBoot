package fr.vbillard.tissusdeprincesseboot.controlers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = FxmlPathProperties.PROPERTY_PREFIX)
public class FxmlPathProperties {

	public static final String PROPERTY_PREFIX = "fx.controller";

	private Resource mainOverview;
	private Resource root;
	private Resource tissuEditDialog ;
	private Resource patronEditDialog ;
	private Resource matiereEdit ;
	private Resource typeEdit ;
	private Resource tissageEdit ;
	private Resource genericTextEdit;
	private Resource genericChoiceBoxEdit;
	private Resource longueur;
	private Resource pictureExpended;

	private Resource root2;
	private Resource tissus2;
	private Resource tissuCard;
	private Resource tissuDetail;
	private Resource projetList;
	private Resource projetCard;
	private Resource projetDetail;
	private Resource patronList;
	private Resource patronCard;
	private Resource patronDetail;
}
