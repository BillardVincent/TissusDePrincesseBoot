package fr.vbillard.tissusdeprincesseboot.controller.utils;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ShowAlert {

	public static Optional<ButtonType> suppression(Stage stage, EntityToString entity, String item) {
		String itemDisplay = item == null ? "" : " : " + item;
		return confirmation(stage, "Suppression",
				"Voulez vous vraiment supprimer " + ModelUtils.generateString(entity, Articles.DEMONSTRATIF),
				"Supprimer " + ModelUtils.generateString(entity, Articles.DEFINI) + itemDisplay + " ?");
	}

	public static Optional<ButtonType> suppressionImpossible(Stage stage, EntityToString entity, String item) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage);
		alert.setTitle("Info");
		alert.setHeaderText("Vous ne pouvez pas supprimer " + ModelUtils.generateString(entity, Articles.DEMONSTRATIF));
		String itemDisplay = item == null ? Strings.EMPTY : " : " + item;
		alert.setContentText("Vous ne pouvez pas supprimer " + ModelUtils.generateString(entity, Articles.DEFINI)
				+ itemDisplay + " car "+ (entity.isMasculin() ? "il" : "elle") + "est utilisé"+ (entity.isMasculin() ? "e":
				Strings.EMPTY) +" dans un projet");
		return alert.showAndWait();
	}

	public static Optional<ButtonType> information(Stage stage, String titre, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage);
		alert.setTitle(titre);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait();
	}
	
	public static Optional<ButtonType> confirmation(Stage stage, String titre, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle(titre);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait();
	}

}
