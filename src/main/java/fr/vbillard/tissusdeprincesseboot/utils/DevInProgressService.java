package fr.vbillard.tissusdeprincesseboot.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DevInProgressService {

	public static void notImplemented() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("NON DISPONIBLE");
        alert.setHeaderText("Fonction non implémentée");
        alert.setContentText("Nous travaillons sur cette fonctionnalité. Elle sera bientot disponible !");

        alert.showAndWait();
	}
}
