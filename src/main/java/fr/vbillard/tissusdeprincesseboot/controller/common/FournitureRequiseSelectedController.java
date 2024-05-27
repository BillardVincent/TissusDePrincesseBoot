package fr.vbillard.tissusdeprincesseboot.controller.common;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class FournitureRequiseSelectedController implements IController {

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

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getFournitureRequise() == null) {
			throw new IllegalData();
		}
		fournitureRequise = data.getFournitureRequise();
		if (fournitureRequise != null) {
			String abbreviation = fournitureRequise.getUnite() == null || fournitureRequise.getUnite().getAbbreviation() == null ?
					Strings.EMPTY : fournitureRequise.getUnite().getAbbreviation();
			quantiteLabel.setText(fournitureRequise.getQuantite() + abbreviation);
			typeLabel.setText(fournitureRequise.getTypeName());
			uniteLabel.setText(fournitureRequise.getUnite() == null ? Strings.EMPTY : fournitureRequise.getType().getIntitulePrincipale());

			if (fournitureRequise.getUnite() != null) {
				uniteSecLabel.setText(fournitureRequise.getType().getIntituleSecondaire());
			}
			String abbreviationSec = fournitureRequise.getUniteSecondaire() == null || fournitureRequise.getUniteSecondaire().getAbbreviation() == null ?
					Strings.EMPTY : fournitureRequise.getUniteSecondaire().getAbbreviation();
			quantiteSecLabel.setText(fournitureRequise.getQuantiteSecondaireMin()+"-"+
					fournitureRequise.getQuantiteSecondaireMax()+abbreviationSec);


		} else {
			quantiteLabel.setText("?");
			typeLabel.setText("?");
			uniteLabel.setText("?");
		}

	}

}
