package fr.vbillard.tissusdeprincesseboot.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:chaines.properties")
@ConfigurationProperties(prefix = Constants.PROPERTY_PREFIX)
public class Constants {

	public static final String PROPERTY_PREFIX = "string";

	private String nonEnregistre;
	private String aucuneDescription;
	private String annuler;
	private String valider;
	private String supprimer;

	public static final Paint colorBlack = Color.BLACK;
	public static final Paint colorAdd = Color.GREEN;
	public static final Paint colorDelete = Color.RED;
	public static final Paint colorAccent = Color.BLUE;
	public static final Paint colorWarning = Color.ORANGE;

	public static final Paint colorSecondary = new Color(0.69019607843f, 0.15294117647f, 0.23529411764f, 1);

}