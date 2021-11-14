package fr.vbillard.tissusdeprincesseboot.controlers;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Component
public class PictureExpended implements IController{

	private Stage dialogStage;
	
	@FXML
	ImageView view;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(StageInitializer mainApp, Photo photo) {
		view.setImage(new Image(new ByteArrayInputStream(photo.getData())));
		
	}

	public boolean isOkClicked() {
		return false;
	}


	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {

	}
}
