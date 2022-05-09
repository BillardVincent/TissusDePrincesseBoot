package fr.vbillard.tissusdeprincesseboot.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class TissuRequisCardController implements IController {

	@FXML
	private Label longueurLabel;
	@FXML
	private Label laizeLabel;
	@FXML
	private Label gammePoidsLabel;
	@FXML
	private Label variantsLabel;

	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;

	private RootController rootController;

	private FxData fxData;

	public TissuRequisCardController(RootController rootController) {
		this.rootController = rootController;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuRequis() == null) {
			throw new IllegalData();
		}
		tissuRequis = data.getTissuRequis();
		if (tissuRequis != null) {
			longueurLabel.setText(Integer.toString(tissuRequis.getLongueur()) + " cm");
			laizeLabel.setText(Integer.toString(tissuRequis.getLaize()) + " cm");
			gammePoidsLabel.setText(tissuRequis.getGammePoids());

		} else {
			longueurLabel.setText("");
			laizeLabel.setText("");
			gammePoidsLabel.setText("");
			variantsLabel.setText("");
		}

		variantsLabel.setText(StringUtils.join(tissuRequis.getVariant(), " - "));

		setPane();
	}

	private void setPane() {
		// TODO Auto-generated method stub

	}

	@FXML
	private void chooseTissuSelected() {
		// TODO
		rootController.displaySelected(fxData);
	}
}
