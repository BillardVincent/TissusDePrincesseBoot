package fr.vbillard.tissusdeprincesseboot.controlers;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

@Component
public class GenericTextEditController implements IController{
	private Stage dialogStage;
	private String oldData;
	private String result;
	private StageInitializer mainApp;


	@FXML
	private Label beforeChangeLabel;
	@FXML
	private TextArea textBox;
	@FXML
	private Button valdateBtn;
	@FXML Button cancelBtn;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void handleValidate() {
		result = oldData;
		dialogStage.close();
	}
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	public String result() {
		return result;
	}
	public void setData(StageInitializer mainApp, String value) {
		if (value == null ) value = "";
		this.mainApp = mainApp;
		this.result = value;
		this.oldData = value;
		textBox.setText(oldData);
		beforeChangeLabel.setText(oldData.equals("")? "-- champ vide --" : oldData);
		

		
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {

	}
}
