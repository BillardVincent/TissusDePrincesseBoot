package fr.vbillard.tissusdeprincesseboot.controller.projet;

import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(Utils.PROTOTYPE)
public class ProjetEditTissuListElementController extends ProjetEditListElementController<TissuRequisDto,
		TissuUsed, TissuUsedService, TissuRequis, Tissu> {

	public ProjetEditTissuListElementController(TissuUsedService tissuUsedService) {
		super(tissuUsedService);
	}

	@Override
	protected TissuRequisDto requisFromData(FxData data) {
		return data.getTissuRequis();
	}

	@Override
	protected PathEnum getPathEnumUsed() {
		return PathEnum.TISSU_USED_CARD;
	}

	@Override
	protected void setSubData(FxData subData, TissuUsed tissu) {
		subData.setTissuRequis(dtoRequis);
		subData.setTissuUsed(tissu);
	}

	@Override
	protected PathEnum getPathEnumRequis() {
		return PathEnum.TISSU_REQUIS;
	}

	@Override
	protected List<TissuUsed> refreshLst() {
		return elementUsedService.getTissuUsedByTissuRequisAndProjet(dtoRequis, data.getProjet());
	}

	@Override
	public void displaySelected(FxData data) {
		initializer.getRoot().displayTissuRequisSelected(data);
	}
}
