package fr.vbillard.tissusdeprincesseboot.controlers;

import fr.vbillard.tissusdeprincesseboot.TissusDePrincesseFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChargementController {

    @FXML
    private Label message;
    
    private TissusDePrincesseFxApp mainApp;

@FXML
private void initialize() {
}

public void setMainApp(TissusDePrincesseFxApp mainApp){
	this.mainApp = mainApp;
}

public void setMessage(String message) {
	this.message.setText(message);
}
}