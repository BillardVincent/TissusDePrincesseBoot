package fr.vbillard.tissusdeprincesseboot.controller.utils;

import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.util.Strings;

import java.util.Optional;

public class ShowAlert {

	private ShowAlert(){};

	public static final ButtonType DETAILS = new ButtonType("Détails");
	public static final ButtonType SUPPRIMER = new ButtonType("Supprimer");

	public static Optional<ButtonType> suppression(Stage stage, EntityToString entity, String item) {
		String itemDisplay = item == null ? Strings.EMPTY : " : " + item;
		return confirmation(stage, "Suppression",
				"Voulez vous vraiment supprimer " + ModelUtils.generateString(entity, Articles.DEMONSTRATIF),
				"Supprimer " + ModelUtils.generateString(entity, Articles.DEFINI) + itemDisplay + " ?");
	}

	public static Optional<ButtonType> suppressionImpossible(Stage stage, EntityToString entity, String item) {
		String header = "Vous ne pouvez pas supprimer " + ModelUtils.generateString(entity, Articles.DEMONSTRATIF);
		String itemDisplay = item == null ? Strings.EMPTY : " : " + item;
		String content = "Vous ne pouvez pas supprimer " + ModelUtils.generateString(entity, Articles.DEFINI)
				+ itemDisplay + " car "+ (entity.isMasculin() ? "il" : "elle") + "est utilisé"+ (entity.isMasculin() ? "e":
				Strings.EMPTY) +" dans au moins un projet";
		return information(stage, "Info",  header, content);
	}

	public static Optional<ButtonType> information(Stage stage, String titre, String header, String content) {
		return alert(AlertType.INFORMATION, stage, titre, header, content);
	}
	
	public static Optional<ButtonType> confirmation(Stage stage, String titre, String header, String content) {
		return alert(AlertType.CONFIRMATION, stage, titre, header, content);
	}

  public static  Optional<ButtonType> erreur(Stage stage, String titre, String header, String errorMessage) {
		return alert(AlertType.ERROR, stage, titre, header, errorMessage);
  }

	public static  Optional<ButtonType> warn(Stage stage, String titre, String header, String errorMessage) {
		return alert(AlertType.WARNING, stage, titre, header, errorMessage);
	}

	public static  Optional<ButtonType> alert(AlertType type, Stage stage, String titre, String header,
			String errorMessage){
		Alert alert = new Alert(type);
		if (stage != null){
			alert.initOwner(stage);
		}
		alert.setTitle(titre);
		alert.setHeaderText(header);
		alert.setContentText(errorMessage);

		setStyle(alert);

		return alert.showAndWait();
	}

	public static Optional<ButtonType> detailSuppressionAnnuler(Stage stage, EntityToString entity){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choisissez");
		alert.setHeaderText("Que souhaitez vous faire de " + ModelUtils.generateString(entity,Articles.DEMONSTRATIF));
		alert.initOwner(stage);

		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(DETAILS, SUPPRIMER, ButtonType.CANCEL);

		setStyle(alert);
		return alert.showAndWait();
	}

	private static void setStyle(Alert alert) {
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/style/style.css");
		dialogPane.getStylesheets().add("/style/modena.css");
	}
}
