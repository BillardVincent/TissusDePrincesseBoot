package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import org.springframework.stereotype.Service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.AbstractRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.FxUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

@Service
public class TissuPatronEditHelper extends PatronEditHelper<Tissu, TissuVariant, TissuRequis, TissuUsed>{
	

	
	public TissuPatronEditHelper(TissuRequisService requisService) {
		this.requisService = requisService;
	}

	@Override
	protected void completeTopGrid(GridPane topGrid, AbstractRequisDto<TissuRequis, Tissu> dto, JFXButton validateBtn) {
		TissuRequisDto tissu = (TissuRequisDto) dto;
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
			saveRequis(tissu);
		});	
	}

	@Override
	void saveRequis(FxDto requis) {
		boolean edit = requis.getId() != 0;
		TissuRequisDto tissuReturned = requisService.createOrUpdate(tissu, patron);
		if (!edit) {
			patron.getTissusRequis().add(tissu);
		}
		loadTissuRequisForPatron();
		displayTissuRequis(tissuReturned);		
	}

	@Override
	void deleteRequis(FxDto requis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void completeTopGrid(GridPane topGrid, , JFXButton validateBtn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void saveRequis(FxDto<TissuRequis> requis, Patron patron) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void deleteRequis(FxDto<TissuRequis> requis) {
		// TODO Auto-generated method stub
		
	}




}
