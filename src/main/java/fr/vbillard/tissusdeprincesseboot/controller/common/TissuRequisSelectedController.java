package fr.vbillard.tissusdeprincesseboot.controller.common;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.LaizeLongueurOptionCell;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisLaizeOptionService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class TissuRequisSelectedController implements IController {

	@FXML
	private GridPane longueurLaizeGrid;
	@FXML
	private Label gammePoidsLabel;
	@FXML
	private Label variantsLabel;
	@FXML
	private Label titleLbl;
	@FXML
	private HBox typeHbx;
	@FXML
	private Label typeLbl;
	@FXML
	private HBox matiereHbx;
	@FXML
	private Label matiereLbl;
	@FXML
	private HBox tissageHbx;
	@FXML
	private Label tissageLbl;

	private final TissuRequisLaizeOptionService trloService;

	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;

	public TissuRequisSelectedController(TissuRequisLaizeOptionService trloService) {
		this.trloService = trloService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuRequis() == null) {
			throw new IllegalData();
		}
		tissuRequis = data.getTissuRequis();

		List<TissuRequisLaizeOption> tissuRequisLaizeOptionList = trloService
				.getTissuRequisLaizeOptionByRequisId(tissuRequis.getId());

		for (int i = 0; i < tissuRequisLaizeOptionList.size();) {
			TissuRequisLaizeOption trlo = tissuRequisLaizeOptionList.get(i);

			LaizeLongueurOptionCell laizeBox = new LaizeLongueurOptionCell(trlo.getLaize());
			laizeBox.getStyleClass().addAll(ClassCssUtils.GRID_CELL, ClassCssUtils.LEFT_COLUMN);
			LaizeLongueurOptionCell longueurBox = new LaizeLongueurOptionCell(trlo.getLongueur());

			longueurLaizeGrid.addRow(++i, laizeBox, longueurBox);
			longueurLaizeGrid.getRowConstraints().add(new RowConstraints(30));

		}

		gammePoidsLabel.setText("Poids : " + StringUtils.join(tissuRequis.getGammePoids(), Utils.OR));

		if (tissuRequis.isDoublure()) {
			titleLbl.setText(titleLbl.getText() + " (doublure)");
		}

		setLabelWithOrCollection(tissuRequis.getMatiere(), matiereHbx, matiereLbl);
		setLabelWithOrCollection(tissuRequis.getTissage(), tissageHbx, tissageLbl);
		typeLbl.setText(
				tissuRequis.getTypeTissu() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissuRequis.getTypeTissu().getLabel());

	}

	private void setLabelWithOrCollection(List data, HBox content, Label label) {
		if (CollectionUtils.isEmpty(data)) {
			content.setVisible(false);
		} else {
			label.setText(StringUtils.join(StringUtils.join(tissuRequis.getGammePoids(), Utils.OR)));
		}
	}

}
