package fr.vbillard.tissusdeprincesseboot.controller.common;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Component
public class SetWebUrlDialogController implements IModalController {
	private Stage dialogStage;
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
		result = new FxData();
	}

	@FXML
	public void handleValidate() {
		if (urlField.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aucune valeur renseignée");
			alert.setHeaderText("Aucune valeur renseignée");
			alert.setContentText("Veuillez renseigner une URL");
			alert.showAndWait();
		} else if (!validateAsPicture()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Non supporté");
			alert.setHeaderText("Le format n'est pas supporté");
			alert.setContentText("Le format n'est pas supporté. L'URL doit terminer par "
					+ Strings.join(ImageFormat.extensions(), ' '));
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

	private boolean validateAsPicture() {
		ImageFormat[] imagesFormats = ImageFormat.values();
		String urlTrimmed = urlField.getText().trim();
		int dotIndex = urlTrimmed.lastIndexOf(".");
		if (dotIndex == -1) {
			return false;
		}
		String extension = urlTrimmed.substring(dotIndex + 1);
		for (ImageFormat format : imagesFormats) {
			if (format.getValue().equals(extension.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	@FXML
	public void handleCancel() {
		dialogStage.close();
	}

	@FXML
	public void pastClipboard() {
		try {
			System.setProperty("java.awt.headless", "false");
			String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
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
