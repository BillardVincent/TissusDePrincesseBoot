package fr.vbillard.tissusdeprincesseboot.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class FournitureRequiseCardController implements IController {

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

	public FournitureRequiseCardController(RootController rootController) {
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
			variantsLabel.setText(StringUtils.join(tissuRequis.getVariant(), " - "));

		} else {
			longueurLabel.setText("");
			laizeLabel.setText("");
			gammePoidsLabel.setText("");
			variantsLabel.setText("");
		}
		setPane();
	}

	private void setPane() {
		/* TODO améliorer l'affichage de la carte
		 -> ajouter un badge si incomplet/OK/avec warning
		 warning : si longueur comprise dans la marge (ou supérieure) ; si un laize est <;
		*/
	}

	@FXML
	private void viewDetails() {
		/*
		TODO voir les détails des problemes
		 */
		// rootController.displaySelected(fxData);
	}
}
