package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ViewListController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TissusController extends ViewListController {

	private TissuService tissuService;
	private RootController rootController;

	public TissusController(TissuService tissuService, RootController rootController) {
		page = 0;
		this.tissuService = tissuService;
		this.rootController = rootController;
	}

	@Override
	protected void setElements() {
		cardPane.getChildren().clear();
		List<TissuDto> lstTissu;
		if (specification instanceof TissuSpecification) {
			lstTissu = tissuService.getObservablePage(page, PAGE_SIZE, (TissuSpecification) specification);
		} else {
			lstTissu = tissuService.getObservablePage(page, PAGE_SIZE);
		}

		for (TissuDto t : lstTissu) {
			FxData data = new FxData();
			data.setTissu(t);
			Pane card = initializer.displayPane(PathEnum.TISSUS_CARD, data);
			cardPane.getChildren().add(card);
		}
		setPageInfo(tissuService.count());
	}

	@Override
	@FXML
	public void addNewElement(MouseEvent mouseEvent) {
		rootController.displayTissusEdit(new TissuDto());
	}

}
