package fr.vbillard.tissusdeprincesseboot.controller.patron.detail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.LaizeLongueurOptionCell;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisLaizeOptionService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

@Component
@Scope(Utils.PROTOTYPE)
public class TissuDisplayController implements IController {

	@FXML
	Label tissageValues;
	@FXML
	Label matiereValues;
	@FXML
	GridPane longueurLaizeGrid;
	@FXML
	Label titre; 

	
	private final RootController rootController;
	private StageInitializer initializer;
	
	private final TissuRequisLaizeOptionService tissuRequisLaizeOptionService;

	private TissuRequisDto tissuRequis;

	TissuDisplayController(RootController rootController, TissuRequisLaizeOptionService tissuRequisLaizeOptionService) {
		this.rootController = rootController;
		this.tissuRequisLaizeOptionService = tissuRequisLaizeOptionService;

	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuRequis() == null) {
			throw new IllegalData();
		}
		
		tissuRequis = data.getTissuRequis();
		
		String rank = Strings.EMPTY;
		if (data.getRank() != null) {
			rank = String.valueOf(data.getRank()+1) + ". ";
		}
		
		String typeTissu;
		if (tissuRequis.getTypeTissu() != null) {
			typeTissu = ModelUtils.startWithMajuscule(tissuRequis.getTypeTissu().getLabel());
		} else {
			typeTissu = "Tissu";
		}
		titre.setText(rank + typeTissu);
		
		tissageValues.setText(StringUtils.join(tissuRequis.getTissage(), Utils.COMMA));
		matiereValues.setText(StringUtils.join(tissuRequis.getMatiere(), Utils.COMMA));
		
	    List<TissuRequisLaizeOption> tissuRequisLaizeOptionList =
	            tissuRequisLaizeOptionService.getTissuRequisLaizeOptionByRequisId(tissuRequis.getId());

		for (int i = 0; i < tissuRequisLaizeOptionList.size();) {
			TissuRequisLaizeOption trlo = tissuRequisLaizeOptionList.get(i);

			LaizeLongueurOptionCell laizeBox = new LaizeLongueurOptionCell(trlo.getLaize());
			laizeBox.getStyleClass().addAll(ClassCssUtils.GRID_CELL, ClassCssUtils.LEFT_COLUMN);
			LaizeLongueurOptionCell longueurBox = new LaizeLongueurOptionCell(trlo.getLongueur());

			longueurLaizeGrid.addRow(++i, laizeBox, longueurBox);
			longueurLaizeGrid.getRowConstraints().add(new RowConstraints(30));

		}
	}
	
}
