package fr.vbillard.tissusdeprincesseboot.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import de.jensd.fx.glyphs.GlyphIcon;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
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
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

@Component
public class TissuRequisCardController implements IController {

	public static final String SEPARATOR = " - ";
	public static final String DELIMITER = ", ";
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
	@FXML
	private HBox complement;

	private final TissuUsedService tissuUsedService;
	private final UserPrefService preferenceService;

	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;
	private ProjetDto projet;

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
			variantsLabel.setText(StringUtils.join(tissuRequis.getVariant(), SEPARATOR));
			doublure.setVisible(tissuRequis.isDoublure());

		} else {
			longueurLabel.setText(Strings.EMPTY);
			laizeLabel.setText(Strings.EMPTY);
			gammePoidsLabel.setText(Strings.EMPTY);
			variantsLabel.setText(Strings.EMPTY);
			doublure.setVisible(false);

		}
		setPane();
	}

	private void setPane() {
		GlyphIcon iconStatus;

		int longueurUtilisee = tissuUsedService.longueurUsedByRequis(tissuRequis, projet);
		List<TissuUsed> tissuUsedTooShort = tissuUsedService.getTissuVariantLaizeTooShort(tissuRequis, projet);
		List<TissuUsed> tissuUsedNotDecati = tissuUsedService.getTissuUsedNotDecati(tissuRequis, projet);

		if (longueurUtilisee < tissuRequis.getLongueur() - tissuRequis.getLongueur() * preferenceService.getUser().getLongueurMargePercent()){
			iconStatus = GlyphIconUtil.notOk();
			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert.information(initializer.getPrimaryStage(),
					"Attention", "Pas assez de tissu", "La longueur de tissu alloué est inférieure à la longueur de tissu "
							+ "requise. Ajoutez d'autres tissus"));
			iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

		} else if (longueurUtilisee < tissuRequis.getLongueur() || !CollectionUtils.isEmpty(tissuUsedTooShort) || !CollectionUtils.isEmpty(tissuUsedNotDecati)) {
			iconStatus = GlyphIconUtil.warning();
			StringBuilder header = new StringBuilder();
			StringBuilder content = new StringBuilder();

			if (longueurUtilisee < tissuRequis.getLongueur()){
				header.append("Pas assez de tissu");
				content.append("La longueur de tissu allouée est inférieure à la longueur de tissu requise.");
			}

			if (!CollectionUtils.isEmpty(tissuUsedTooShort)){

				Utils.appendWithSeparator(header, SEPARATOR, "La laize est trop courte");
				Utils.appendWithSeparator(content, SEPARATOR, "Laize trop courte pour ");
				content.append(tissuUsedTooShort.stream().map(u -> u.getTissu().getReference()).collect(Collectors.joining(
						DELIMITER)));

			}

			if (!CollectionUtils.isEmpty(tissuUsedNotDecati)){
				boolean plusieursTissus = tissuUsedNotDecati.size() > 1;

				String tissu_s = ModelUtils.generateString(EntityToString.TISSU, Articles.DEFINI , plusieursTissus, true);
				Utils.appendWithSeparator(header, SEPARATOR, tissu_s);
				header.append(" non décati");
				header.append(plusieursTissus ? "s" : Strings.EMPTY);

				Utils.appendWithSeparator(content, SEPARATOR,	tissu_s);
				content.append(tissuUsedNotDecati.stream().map(u -> u.getTissu().getReference()).collect(Collectors.joining(
						DELIMITER)));
				content.append( plusieursTissus ? " ne sont pas décatis" : " n'est pas décati ");
			}

			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert.information(initializer.getPrimaryStage(),
					"Attention", header.toString(), content.toString()));
			iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

		} else {
			iconStatus = GlyphIconUtil.ok();
		}
	complement.getChildren().add(iconStatus);

	}
}
