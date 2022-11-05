package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@Component
public class FournitureController extends ViewListController {

	private FournitureService fournitureService;
	private RootController rootController;

	public FournitureController(FournitureService fournitureService, RootController rootController) {
		page = 0;
		this.fournitureService = fournitureService;
		this.rootController = rootController;
	}

	@Override
	protected void setElements() {
		cardPane.getChildren().clear();
		List<FournitureDto> lstTissu;
		if (specification instanceof FournitureSpecification) {
			lstTissu = fournitureService.getObservablePage(page, PAGE_SIZE, (FournitureSpecification) specification);
		} else {
			lstTissu = fournitureService.getObservablePage(page, PAGE_SIZE);

		}
		for (FournitureDto t : lstTissu) {
			FxData data = new FxData();
			data.setFourniture(t);
			Pane card = initializer.displayPane(PathEnum.FOURNITURES_CARD, data);
			cardPane.getChildren().add(card);
		}
		setPageInfo(fournitureService.count());
	}

	@Override
	@FXML
	public void addNewElement(MouseEvent mouseEvent) {
		rootController.displayFournitureEdit(new FournitureDto());
	}

}
