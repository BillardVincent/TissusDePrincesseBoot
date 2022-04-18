package fr.vbillard.tissusdeprincesseboot.controller.common;

import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.IntegerSpinner;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IModalController;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
//import javafx.scene.control.Spinner;
//import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

@Component
public class SetWebUrlDialogController implements IModalController {
	private Stage dialogStage;
	private String url;
	private FxData result;
	private StageInitializer initializer;

	@FXML
	private JFXTextField urlField;
	@FXML
	private JFXButton valdateBtn;
	@FXML
	private JFXButton cancelBtn;

	@FXML
	private void initialize() {
	}

	@FXML
	public void handleValidate() {
		if (urlField.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(initializer.getPrimaryStage());
			alert.setTitle("Aucune valeur renseignée");
			alert.setHeaderText("Aucune valeur renseignée");
			alert.setContentText("Veuillez renseigner l'URL d'une image");
			alert.showAndWait();
		} else {
			result = new FxData();
			try {
				result.setUrl(new URL(urlField.getText()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new NotFoundException(urlField.getText());
			}
			dialogStage.close();
		}

	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	@FXML
	public void pastClipboard() {
	try {
		String data = (String) Toolkit.getDefaultToolkit()
		        .getSystemClipboard().getData(DataFlavor.stringFlavor);
		urlField.setText(data);
	} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
		e.printStackTrace();
		throw new NotFoundException("Le contenu du presse papier");
	} 
	}
	
	@Override
	public FxData result() {
		return result;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		

	}
	
	

}
