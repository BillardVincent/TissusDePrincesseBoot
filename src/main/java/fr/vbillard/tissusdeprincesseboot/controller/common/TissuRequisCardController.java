package fr.vbillard.tissusdeprincesseboot.controller.common;

import de.jensd.fx.glyphs.GlyphIcon;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.LaizeLongueurOptionCell;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisLaizeOptionService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TissuRequisCardController implements IController {

	@FXML
	private GridPane longueurLaizeGrid;
	@FXML
	private Label gammePoidsLabel;
	@FXML
	private Label variantsLabel;
	@FXML
	private Label doublure;
	@FXML
	private HBox doublurHbx;
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

	private final TissuUsedService tissuUsedService;
	private final UserPrefService preferenceService;
	private final TissuRequisLaizeOptionService trloService;

	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;
	private ProjetDto projet;

	public TissuRequisCardController(TissuUsedService tissuUsedService, UserPrefService preferenceService,
			TissuRequisLaizeOptionService trloService) {
		this.tissuUsedService = tissuUsedService;
		this.preferenceService = preferenceService;
		this.trloService = trloService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuRequis() == null || data.getProjet() == null) {
			throw new IllegalData();
		}
		tissuRequis = data.getTissuRequis();
		projet = data.getProjet();

			List<TissuRequisLaizeOption> tissuRequisLaizeOptionList = trloService
					.getTissuRequisLaizeOptionByRequisId(tissuRequis.getId());

			for (int i = 0; i < tissuRequisLaizeOptionList.size(); i++) {
				TissuRequisLaizeOption trlo = tissuRequisLaizeOptionList.get(i);

				LaizeLongueurOptionCell laizeBox = new LaizeLongueurOptionCell(trlo.getLaize());
				laizeBox.getStyleClass().addAll(ClassCssUtils.GRID_CELL, ClassCssUtils.LEFT_COLUMN);
				LaizeLongueurOptionCell longueurBox = new LaizeLongueurOptionCell(trlo.getLongueur());

				longueurLaizeGrid.addRow(i + 1, laizeBox, longueurBox);
				longueurLaizeGrid.getRowConstraints().add(new RowConstraints(30));

			}
			gammePoidsLabel.setText(StringUtils.join(tissuRequis.getGammePoids(), Utils.OR));
			//TODO
			//doublure.setVisible(tissuRequis.isDoublure());
			setLabelWithOrCollection(tissuRequis.getMatiere(), matiereHbx, matiereLbl);
			setLabelWithOrCollection(tissuRequis.getTissage(), tissageHbx, tissageLbl);
			typeLbl.setText(tissuRequis.getTypeTissu() == null ?
					TypeTissuEnum.NON_RENSEIGNE.label : tissuRequis.getTypeTissu().getLabel());

			setIcones();
	}

	private void setIcones() {
		GlyphIcon<?> iconStatus;

		int longueurMin = trloService.getLongueurMinByRequis(tissuRequis.getId());
		int longueurUtilisee = tissuUsedService.longueurUsedByRequis(tissuRequis, projet);

		List<TissuUsed> tissuUsedTooShort = tissuUsedService.getTissuVariantLaizeTooShort(tissuRequis, projet);
		List<TissuUsed> tissuUsedNotDecati = tissuUsedService.getTissuUsedNotDecati(tissuRequis, projet);

		if (longueurUtilisee < longueurMin - longueurMin * preferenceService.getUser().getLongueurMargePercent()) {
			iconStatus = GlyphIconUtil.notOk();
			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED,
					e -> ShowAlert.information(initializer.getPrimaryStage(), "Attention", "Pas assez de tissu",
							"La longueur de tissu alloué est inférieure à la longueur de tissu "
									+ "requise. Ajoutez d'autres tissus"));
			iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

		} else if (longueurUtilisee < longueurMin || !CollectionUtils.isEmpty(tissuUsedTooShort)
				|| !CollectionUtils.isEmpty(tissuUsedNotDecati)) {
			iconStatus = GlyphIconUtil.warning();
			StringBuilder header = new StringBuilder();
			StringBuilder content = new StringBuilder();

			getMessageForLongueur(longueurUtilisee, longueurMin, header, content);

			getMessageForLaize(tissuUsedTooShort, header, content);

			getMesageForDecati(tissuUsedNotDecati, header, content);

			iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert
					.information(initializer.getPrimaryStage(), "Attention", header.toString(), content.toString()));
			iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

		} else {
			iconStatus = GlyphIconUtil.ok();
		}

		//TODO doublurHbx.getChildren().add(iconStatus);

	}

	private static void getMesageForDecati(List<TissuUsed> tissuUsedNotDecati, StringBuilder header, StringBuilder content) {
		if (!CollectionUtils.isEmpty(tissuUsedNotDecati)) {
			boolean plusieursTissus = tissuUsedNotDecati.size() > 1;

			String tissuS = ModelUtils.generateString(EntityToString.TISSU, Articles.DEFINI, plusieursTissus,
					true);
			Utils.appendWithSeparator(header, Utils.SEPARATOR, tissuS);
			header.append(" non décati");
			header.append(plusieursTissus ? "s" : Strings.EMPTY);

			Utils.appendWithSeparator(content, Utils.SEPARATOR, tissuS);
			content.append(tissuUsedNotDecati.stream().map(u -> u.getTissu().getReference())
					.collect(Collectors.joining(Utils.COMMA)));
			content.append(plusieursTissus ? " ne sont pas décatis" : " n'est pas décati ");
		}
	}

	private static void getMessageForLaize(List<TissuUsed> tissuUsedTooShort, StringBuilder header, StringBuilder content) {
		if (!CollectionUtils.isEmpty(tissuUsedTooShort)) {

			Utils.appendWithSeparator(header, Utils.SEPARATOR, "La laize est trop courte");
			Utils.appendWithSeparator(content, Utils.SEPARATOR, "Laize trop courte pour ");
			content.append(tissuUsedTooShort.stream().map(u -> u.getTissu().getReference())
					.collect(Collectors.joining(Utils.COMMA)));
		}
	}

	private static void getMessageForLongueur(int longueurUtilisee, int longueurMin, StringBuilder header, StringBuilder content) {
		if (longueurUtilisee < longueurMin) {
			header.append("Pas assez de tissu");
			content.append("La longueur de tissu allouée est inférieure à la longueur de tissu requise.");
		}
	}

	private void setLabelWithOrCollection(List<?> data, HBox content, Label label) {
		if (CollectionUtils.isEmpty(data)){
			content.setVisible(false);
		} else {
			label.setText(StringUtils.join(StringUtils.join(tissuRequis.getGammePoids(), Utils.OR)));
		}
	}
}
