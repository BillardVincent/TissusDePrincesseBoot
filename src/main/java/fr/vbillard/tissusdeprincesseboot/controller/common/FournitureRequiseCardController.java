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
	private Label uniteLabel;
	@FXML
	private Label quantiteLabel;
	@FXML
	private Label uniteSecLabel;
	@FXML
	private Label quantiteSecLabel;
	@FXML
	private Label typeLabel;

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
			quantiteLabel.setText(fournitureRequise.getQuantite()+fournitureRequise.getUnite().getAbbreviation());
			typeLabel.setText(fournitureRequise.getTypeName());
			uniteLabel.setText(fournitureRequise.getType().getIntitulePrincipale());

			uniteSecLabel.setText(fournitureRequise.getType().getIntituleSecondaire());
			quantiteSecLabel.setText(fournitureRequise.getQuantiteSecondaireMin()+"-"+
					fournitureRequise.getQuantiteSecondaireMax()+fournitureRequise.getUniteSecondaire().getAbbreviation());


		} else {
			quantiteLabel.setText("?");
			typeLabel.setText("?");
			uniteLabel.setText("?");
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
