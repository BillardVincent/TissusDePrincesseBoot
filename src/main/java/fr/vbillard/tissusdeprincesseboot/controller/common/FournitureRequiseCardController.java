package fr.vbillard.tissusdeprincesseboot.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
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
	private FournitureRequiseDto fournitureRequise;

	private RootController rootController;

	private FxData fxData;

	public FournitureRequiseCardController(RootController rootController) {
		this.rootController = rootController;
	}

	/////////// TODO ////////////////////////
	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getFournitureRequise() == null) {
			throw new IllegalData();
		}
		fournitureRequise = data.getFournitureRequise();
		if (fournitureRequise != null) {
			longueurLabel.setText(Float.toString(fournitureRequise.getQuantite()) + " cm");
			laizeLabel.setText("TODO"+ " cm");
			gammePoidsLabel.setText("TODO");
			variantsLabel.setText("TODO");

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
