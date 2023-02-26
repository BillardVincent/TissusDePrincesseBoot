package fr.vbillard.tissusdeprincesseboot.controller.utils;

import java.sql.CallableStatement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@PropertySource("classpath:views.properties")
@ConfigurationProperties(prefix = FxmlPathProperties.PROPERTY_PREFIX)
public class FxmlPathProperties {

	public static final String PROPERTY_PREFIX = "fx.controller";

	private Resource matiereEdit;
	private Resource typeEdit;
	private Resource tissageEdit;

	private Resource root;
	private Resource tissus;
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
	private Resource projetEditTissuListElement;
	private Resource projetEditFournitureListElement;
	private Resource tissuRequisCard;
	private Resource tissuUsedCard;
	private Resource tissuRequisSelected;
	private Resource plusCard;
	private Resource longueur;
	private Resource quantite;
	private Resource url;
	private Resource tissuSearch;
	private Resource projetSearch;
	private Resource patronSearch;
	private Resource checkBoxChoice;
	private Resource preference;
	private Resource carrousel;
	private Resource inventaire;
	private Resource fournitureCarrousel;
	private Resource fournitureSearch;
	private Resource fournitureRequisCard;
	private Resource fournitureUsedCard;
	private Resource fournitureRequisSelected;
	private Resource typeFournitureEdit;
	private Resource fourniture;
	private Resource fournitureCard;
	private Resource fournitureDetail;
	private Resource fournitureEdit;

}
