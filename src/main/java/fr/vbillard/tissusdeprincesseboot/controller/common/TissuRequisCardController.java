package fr.vbillard.tissusdeprincesseboot.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import de.jensd.fx.glyphs.GlyphIcon;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
	@FXML
	private Label doublure;

	private final TissuUsedService tissuUsedService;
	private final UserPrefService preferenceService;

	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;
	private ProjetDto projet;

	private FxData fxData;

	public TissuRequisCardController(TissuUsedService tissuUsedService, UserPrefService preferenceService) {
		this.tissuUsedService = tissuUsedService;
		this.preferenceService = preferenceService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuRequis() == null || data.getProjet() == null) {
			throw new IllegalData();
		}
		tissuRequis = data.getTissuRequis();
		projet = data.getProjet();
		if (tissuRequis != null) {
			longueurLabel.setText(tissuRequis.getLongueur() + " cm");
			laizeLabel.setText(tissuRequis.getLaize() + " cm");
			gammePoidsLabel.setText(tissuRequis.getGammePoids());
			variantsLabel.setText(StringUtils.join(tissuRequis.getVariant(), " - "));
			doublure.setVisible(tissuRequis.isDoublure());

		} else {
			longueurLabel.setText("");
			laizeLabel.setText("");
			gammePoidsLabel.setText("");
			variantsLabel.setText("");
			doublure.setVisible(false);

		}
		setPane();
	}

	private void setPane() {

		//TODO insérer l'icone dans le FXML

		GlyphIcon iconStatus;

		int longueurUtilisee = tissuUsedService.longueurVariantByRequis(tissuRequis, projet);
		List<TissuUsed> tissuUsedTooShort = tissuUsedService.getTissuVariantLaizeTooShort(tissuRequis, projet);
		List<TissuUsed> tissuUsedNotDecati = tissuUsedService.getTissuUsedNotDecati(tissuRequis, projet);

		if (longueurUtilisee < tissuRequis.getLongueur() - tissuRequis.getLongueur() * preferenceService.getUser().getLongueurMargePercent()){
			iconStatus = GlyphIconUtil.notOk();
			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert.information(initializer.getPrimaryStage(), "Attention", "Pas assez de tissu", "content"));

		} else if (longueurUtilisee < tissuRequis.getLongueur() || !CollectionUtils.isEmpty(tissuUsedTooShort) || !CollectionUtils.isEmpty(tissuUsedNotDecati)) {
			iconStatus = GlyphIconUtil.warning();
			String header = Strings.EMPTY;
			String content = Strings.EMPTY;

			if (longueurUtilisee < tissuRequis.getLongueur()){
				header += "Pas assez de tissu";
				content += "La longueur de tissu alloué est inférieure à la longueur de tissu requise.";
			}

			if (!CollectionUtils.isEmpty(tissuUsedTooShort)){
				header = StringUtils.join(" - ", "La laize est trop courte");
				content += StringUtils.join(" - ", "Laize trop courte pour "
						+ tissuUsedTooShort.stream().map(u -> u.getTissu().getReference()).collect(Collectors.joining(", ")));
			}

			String finalHeader = header;
			String finalContent = content;
			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert.information(initializer.getPrimaryStage(),
					"Attention", finalHeader, finalContent));

		} else {
			iconStatus = GlyphIconUtil.ok();

		}

		HBox container = new HBox(iconStatus);

	}
}
