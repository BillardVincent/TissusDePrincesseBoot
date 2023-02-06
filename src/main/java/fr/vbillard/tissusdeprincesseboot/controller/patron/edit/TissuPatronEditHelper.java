package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.TissuVariantService;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@Service
public class TissuPatronEditHelper extends PatronEditHelper<Tissu, TissuVariant, TissuRequis, TissuUsed, TissuDto,
		TissuVariantDto, TissuRequisDto>{

	private final MatiereService matiereService;
	private final TissageService tissageService;

	public TissuPatronEditHelper(TissuRequisService requisService, TissuVariantService variantService,
			TissuUsedService usedService, MatiereService matiereService, TissageService tissageService) {
		this.variantService = variantService;
		this.usedService = usedService;
		this.requisService = requisService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
	}

	@Override
	protected void completeTopGrid(GridPane topGrid,TissuRequisDto tissu, JFXButton validateBtn) {
		topGrid.add(new Label("Longeur"), 0, 0);
		topGrid.add(new Label("Laize"), 0, 1);
		topGrid.add(new Label("Gamme de poids"), 0, 2);	
		
		JFXTextField longueurSpinner = FxUtils.buildSpinner(tissu.getLongueurProperty());
		topGrid.add(longueurSpinner, 1, 0);

		JFXTextField laizeSpinner = FxUtils.buildSpinner(tissu.getLaizeProperty());
		topGrid.add(laizeSpinner, 1, 1);

		JFXComboBox<String> gammePoidsChBx = FxUtils.buildComboBox(GammePoids.labels(),tissu.getGammePoidsProperty(), GammePoids.NON_RENSEIGNE.label);
		topGrid.add(gammePoidsChBx, 1, 2);
		
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			tissu.setLaize(Integer.parseInt(laizeSpinner.getText()));
			tissu.setLongueur(Integer.parseInt(longueurSpinner.getText()));
			saveRequis(tissu, patron);
		});	
	}

	@Override
	protected void addToPatron(TissuRequisDto requis, PatronDto patron) {
		patron.getTissusRequis().add(requis);
	}

	@Override
	protected HBox completeLoadBottomRightVBox(JFXButton addTvBtn, TissuRequisDto requis) {
		 if (variantSelected == null ) {
			 variantSelected = new TissuVariantDto();
		 }
		JFXComboBox<String> typeField = FxUtils.buildComboBox(TypeTissuEnum.labels(), variantSelected.getTypeTissuProperty());

		JFXComboBox<String> matiereField = FxUtils.buildComboBox(matiereService.getAllMatieresValues(), variantSelected.getMatiereProperty());

		JFXComboBox<String> tissageField = FxUtils.buildComboBox(tissageService.getAllValues(), variantSelected.getTissageProperty());

		addTvBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

			variantSelected.setMatiere(matiereField.getValue());
			variantSelected.setTissage(tissageField.getValue());
			variantSelected.setTypeTissu(typeField.getValue());
			variantSelected.setTissuRequisId(requis.getId());
			variantSelected = variantService.saveOrUpdate(variantSelected);
			if (variantSelected.getId() == 0) {
				tvList.add(variantSelected);
			}
			loadRequisForPatron();
			displayRequis(requis);
		});



		return new HBox(typeField, matiereField, tissageField, addTvBtn);
	}

	@Override
	protected void setRequisToPatron() {
		patron.setTissusRequis(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
	}

	@Override
	protected void displayVariant(GridPane bottomGrid, TissuVariantDto tv, int index) {
		bottomGrid.add(new Label(Utils.safeString(tv.getTypeTissu()) + " " + Utils.safeString(tv.getMatiere())
				+ " " + Utils.safeString(tv.getTissage())), 0, index * 2);
	}

	@Override
	protected List<TissuRequisDto> getListRequisFromPatron() {
		if (patron.getTissusRequisProperty() != null && patron.getTissusRequis() != null) {
			return patron.getTissusRequis();
		} else
			return Collections.emptyList();
	}

	@Override
	protected boolean hasVariant() {
		return true;
	}

	@Override
	protected Class<Tissu> getEntityClass() {
		return Tissu.class;
	}
}
