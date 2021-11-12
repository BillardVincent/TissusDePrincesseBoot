package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.Optional;

import fr.vbillard.tissusdeprincesseboot.utils.modelToString.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.modelToString.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.modelToString.ModelUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ShowAlert {
	
	public static Optional<ButtonType>suppression(Stage stage, EntityToString entity, String item){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("Suppression");
		alert.setHeaderText("Voulez vous vraiment supprimer "+ ModelUtils.generateString(entity, Articles.DEMONSTRATIF));
		String itemDisplay = item ==null ? "":" : " + item ;
		alert.setContentText("Supprimer "+ ModelUtils.generateString(entity, Articles.DEFINI)+itemDisplay+ " ?");

		return alert.showAndWait();
	}

}
