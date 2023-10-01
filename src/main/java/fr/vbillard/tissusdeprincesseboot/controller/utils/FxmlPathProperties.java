package fr.vbillard.tissusdeprincesseboot.controller.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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
	private Resource tissuSearch;
	private Resource carrousel;

	private Resource tissuRequisCard;
	private Resource tissuRequisSelected;
	private Resource tissuRequisAccordion;

	private Resource tissuUsedCard;

	private Resource projetList;
	private Resource projetCard;
	private Resource projetDetail;
	private Resource projetEdit;
	private Resource projetSearch;

	private Resource listElement;
	private Resource projetEditTissuListElement;
	private Resource projetEditFournitureListElement;

	private Resource patronList;
	private Resource patronCard;
	private Resource patronDetail;
	private Resource patronDetailTissuDisplay;
	private Resource patronDetailVersionDisplay;
	
	private Resource patronEdit;
	private Resource patronVersionAccordion;
	private Resource patronEditPatronVersion;
	private Resource patronEditTissuRequis;
	private Resource patronEditFournitureRequise;
	private Resource patronEditPatron;
	private Resource patronSearch;

	private Resource fourniture;
	private Resource fournitureCard;
	private Resource fournitureDetail;
	private Resource fournitureEdit;
	private Resource fournitureSearch;
	private Resource fournitureCarrousel;

	private Resource fournitureUsedCard;

	private Resource fournitureRequisCard;
	private Resource fournitureRequisSelected;
	private Resource fournitureRequiseAccordion;

	private Resource typeFournitureEdit;

	private Resource newRangementModale;
	private Resource rangementTree;
	private Resource rootList;

	private Resource plusCard;
	private Resource longueur;
	private Resource quantite;
	private Resource url;
	private Resource checkBoxChoice;
	private Resource preference;
	private Resource inventaire;
	
}