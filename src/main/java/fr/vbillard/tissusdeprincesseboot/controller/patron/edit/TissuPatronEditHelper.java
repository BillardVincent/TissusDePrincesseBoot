package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildComboBox;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@Service
public class TissuPatronEditHelper extends PatronEditHelper<Tissu, TissuRequis, TissuUsed, TissuDto,
		 TissuRequisDto>{

	private final MatiereService matiereService;
	private final TissageService tissageService;

	public TissuPatronEditHelper(PatronService patronService, TissuRequisService requisService,
			TissuUsedService usedService, MatiereService matiereService, TissageService tissageService) {
		this.usedService = usedService;
		this.requisService = requisService;
		this.matiereService = matiereService;
		this.tissageService = tissageService;
		this.patronService = patronService;
	}

	protected void completeTopGrid(GridPane topGrid,TissuRequisDto tissu, JFXButton validateBtn) {
		topGrid.add(new Label("Longeur"), 0, 0);
		topGrid.add(new Label("Laize"), 0, 1);
		topGrid.add(new Label("Gamme de poids"), 0, 2);
		topGrid.add(new Label("Doublure"), 0, 3);
		/*
		JFXTextField longueurSpinner = FxUtils.buildSpinner(tissu.getLongueurProperty());
		topGrid.add(longueurSpinner, 1, 0);

		JFXTextField laizeSpinner = FxUtils.buildSpinner(tissu.getLaizeProperty());
		topGrid.add(laizeSpinner, 1, 1);

		 */

		/*JFXComboBox<String> gammePoidsChBx = buildComboBox(GammePoids.labels(),tissu.getGammePoidsProperty(),
				GammePoids.NON_RENSEIGNE.label);
		topGrid.add(gammePoidsChBx, 1, 2);

		JFXCheckBox isDoublure = new JFXCheckBox();
		isDoublure.setSelected(tissu.isDoublure());
		topGrid.add(isDoublure, 1, 3);

		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			tissu.setGammePoids(gammePoidsChBx.getValue());
			//tissu.setLaize(Integer.parseInt(laizeSpinner.getText()));
			//tissu.setLongueur(Integer.parseInt(longueurSpinner.getText()));
			tissu.setDoublure(isDoublure.isSelected());
			//saveRequis(tissu, patron);
		});

		 */
	}

	@Override
	protected void addToPatron(TissuRequisDto requis, PatronDto patron) {

	}

	@Override
	protected HBox completeLoadBottomRightVBox(JFXButton addTvBtn, TissuRequisDto requis) {
		return null;
	}


	@Override
	protected void setRequisToPatron() {
		//patron.setTissusRequis(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
	}

	@Override
	protected List<TissuRequisDto> getListRequisFromPatron() {
		return null;
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
