package fr.vbillard.tissusdeprincesseboot.utils;

import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DevInProgressService {

	public static void notImplemented() {
    ShowAlert.information(null, "NON DISPONIBLE", "Fonction non implémentée",
        "Nous travaillons sur cette fonctionnalité. Elle sera bientot disponible !");
  }
}
