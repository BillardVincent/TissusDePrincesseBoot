package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@Component
public class ProjetListController extends ViewListController {

	private ProjetService projetService;
	private RootController rootController;

	public ProjetListController(ProjetService projetService, RootController rootController) {
		this.projetService = projetService;
		this.rootController = rootController;
		page = 0;
	}

	@Override
	protected void setElements() {
		cardPane.getChildren().clear();

		List<ProjetDto> lst;
		if (specification != null && specification instanceof ProjetSpecification) {
			lst = projetService.getObservablePage(page, PAGE_SIZE, (ProjetSpecification) specification);
		} else {
			lst = projetService.getObservablePage(page, PAGE_SIZE);

		}

		for (ProjetDto p : lst) {
			FxData data = new FxData();
			data.setProjet(p);
			Pane card = initializer.displayPane(PathEnum.PROJET_CARD, data);
			cardPane.getChildren().add(card);
		}
		setPageInfo(projetService.count());
	}

	@Override
	@FXML
	public void addNewElement(MouseEvent mouseEvent) {
		rootController.displayProjetEdit(new ProjetDto());
	}

}
