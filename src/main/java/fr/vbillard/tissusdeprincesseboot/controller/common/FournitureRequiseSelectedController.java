package fr.vbillard.tissusdeprincesseboot.controller.common;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@Component
public class FournitureRequiseSelectedController implements IController {

	@FXML
	private Label longueurLabel;
	@FXML
	private Label laizeLabel;
	@FXML
	private Label gammePoidsLabel;
	@FXML
	private VBox variantsContainer;

	private StageInitializer initializer;
	private FournitureRequiseDto fournitureRequise;

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getFournitureRequise() == null) {
			throw new IllegalData();
		}
		fournitureRequise = data.getFournitureRequise();
		if (fournitureRequise != null) {
			longueurLabel.setText(Float.toString(fournitureRequise.getQuantite()));
			laizeLabel.setText(fournitureRequise.getTypeName());
			//gammePoidsLabel.setText(tissuRequis.getGammePoids());
			setVariantsContainer();

		} else {
			longueurLabel.setText("?");
			laizeLabel.setText("?");
			gammePoidsLabel.setText("?");
			variantsContainer.getChildren().clear();
		}

	}

	private void setVariantsContainer() {
		// TODO Auto-generated method stub

	}

}
