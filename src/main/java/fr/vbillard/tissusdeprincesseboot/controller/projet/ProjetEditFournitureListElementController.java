package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@Component
@Scope("prototype")
public class ProjetEditFournitureListElementController extends ProjetEditListElementController<FournitureRequiseDto,
		FournitureUsed, FournitureUsedService, FournitureRequise, Fourniture> {

	public ProjetEditFournitureListElementController(FournitureUsedService fournitureUsedService) {
		super(fournitureUsedService);
	}

	@Override
	protected FournitureRequiseDto requisFromData(FxData data) {
		return data.getFournitureRequise();
	}

	@Override
	protected PathEnum getPathEnumUsed() {
		return PathEnum.FOURNITURE_USED_CARD;
	}

	@Override
	protected void setSubData(FxData subData, FournitureUsed tissu) {
		subData.setFournitureRequise(dtoRequis);
		subData.setFournitureUsed(tissu);
	}

	@Override
	protected PathEnum getPathEnumRequis() {
		return PathEnum.FOURNITURE_REQUIS_CARD;
	}

	@Override
	protected List<FournitureUsed> refreshLst() {
		return tissuUsedService.getFournitureUsedByTissuRequisAndProjet(dtoRequis, data.getProjet());
	}

	@Override
	public void displaySelected(FxData data) {
		initializer.getRoot().displayFournitureSelected(data);

	}
}