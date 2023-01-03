package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureVariantDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureVariantService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@Service
public class FourniturePatronEditHelper extends
		PatronEditHelper<Fourniture, FournitureVariant, FournitureRequise, FournitureUsed, FournitureDto, FournitureVariantDto, FournitureRequiseDto> {

	private TypeFournitureService typeFournitureService;

	public FourniturePatronEditHelper(TypeFournitureService typeFournitureService,
			FournitureRequiseService requisService, FournitureVariantService variantService,
			FournitureUsedService usedService) {
		this.variantService = variantService;
		this.usedService = usedService;
		this.requisService = requisService;
		this.typeFournitureService = typeFournitureService;
	}

	@Override
	protected void completeTopGrid(GridPane topGrid, FournitureRequiseDto dto, JFXButton validateBtn) {
		topGrid.add(new Label("Type"), 0, 0);
		JFXComboBox<String> uniteChBx = FxUtils.buildComboBox(Unite.labels(),dto.getUniteProperty());
		
		topGrid.add(uniteChBx, 2, 1);

		JFXComboBox<String> typeChBx = FxUtils.buildComboBox(typeFournitureService.getAllValues(),
				dto.getTypeNameProperty());
		typeChBx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			uniteChBx.setValue(dto.getUnite());
		});
		
		topGrid.add(typeChBx, 1, 0);

		topGrid.add(new Label(dto.getType().getIntitulePrincipale()), 0, 1);
		JFXTextField longueurSpinner = FxUtils.buildSpinner(dto.getQuantiteProperty());
		topGrid.add(longueurSpinner, 1, 1);

		uniteChBx.setValue(dto.getUnite());

		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

			// TODO set DTO !!!!!!!!!!!!!!!!

			saveRequis(dto, patron);
		});

	}

	@Override
	protected void addToPatron(FournitureRequiseDto requis, PatronDto patron) {
		patron.setFournituresRequises(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
	}

	@Override
	protected HBox completeLoadBottomRightVBox(JFXButton addTvBtn, FournitureRequiseDto requis) {
		return null;
	}

	@Override
	void setRequisToPatron() {
		patron.setFournituresRequises(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
	}

	@Override
	protected void displayVariant(GridPane bottomGrid, FournitureVariantDto tv, int index) {
		bottomGrid.add(new Label(Utils.safeString(tv.getTypeName()) + " - " + tv.getIntituleSecondaire() + ":  "
				+ FxUtils.safePropertyToString(tv.getQuantiteSecondaireMinProperty()) + "/"
				+ FxUtils.safePropertyToString(tv.getQuantiteSecondaireMaxProperty())
				+ Utils.safeString(tv.getUniteSecondaire())), 0, index * 2);
	}

	@Override
	protected List<FournitureRequiseDto> getListRequisFromPatron() {
		if (patron.getFournituresRequisesProperty() != null && patron.getFournituresRequises() != null) {
			return patron.getFournituresRequises();
		} else
			return Collections.emptyList();
	}

	@Override
	protected Class<Fourniture> getEntityClass() {
		return Fourniture.class;
	}
}
