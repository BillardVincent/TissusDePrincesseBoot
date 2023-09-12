package fr.vbillard.tissusdeprincesseboot.controller.misc;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventaireBatchController implements IModalController{
	
	
	@FXML
	private VBox box;
	
	private FxData result;
	private Stage dialogStage;

	@Override
	public FxData result() {
		return result;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		List<Inventaire> list = data.getListInventaire();
		result = null;
		
		for (Inventaire i : list) {
			Label lbl = new Label(String.valueOf(i.getTissus().size()));
			JFXButton btn = new JFXButton("GO");
			btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setResult(i));
			HBox vbox = new HBox(lbl, btn);
			box.getChildren().add(vbox);
		}
			
	}
	
	private void setResult(Inventaire inventaire) {
		result = new FxData();
		result.setInventaire(inventaire);
		dialogStage.close();
	}
	
	@FXML
	public void snooze() {
		result = null;
		dialogStage.close();
	}

}
