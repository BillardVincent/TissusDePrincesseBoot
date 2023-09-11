package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.DisplayInventaireService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ViewListController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.InventaireService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

@Component
public class ProjetListController extends ViewListController {

	boolean hasIncompleteInventaire;

	@FXML
	private WebView hasIncompleteInventaireIcn;

	private ProjetService projetService;
	private RootController rootController;
	private DisplayInventaireService displayInventaireService;
	private InventaireService inventaireService;
	private CustomIcon customIcon;

	public ProjetListController(CustomIcon customIcon, InventaireService inventaireService, ProjetService projetService,
			RootController rootController, DisplayInventaireService displayInventaireService) {
		this.projetService = projetService;
		this.rootController = rootController;
		this.displayInventaireService = displayInventaireService;
		this.inventaireService = inventaireService;
		this.customIcon = customIcon;
		page = 0;
	}

	@Override
	protected void setElements() {
		cardPane.getChildren().clear();

		List<ProjetDto> lst;
		ProjetSpecification spec;
		if (specification != null && specification instanceof ProjetSpecification) {
			spec = (ProjetSpecification) specification;
		} else {
			spec = ProjetSpecification.builder().projectStatus(Arrays.asList(ProjectStatus.PLANIFIE,
					ProjectStatus.BROUILLON, ProjectStatus.EN_COURS)).build();
		}
		
		lst = projetService.getObservablePage(page, PAGE_SIZE, spec);

		for (ProjetDto p : lst) {
			FxData data = new FxData();
			data.setProjet(p);
			Pane card = initializer.displayPane(PathEnum.PROJET_CARD, data);
			cardPane.getChildren().add(card);
		}

		setPageInfo(projetService.count());
		setInventaireIcone();
	}

	@Override
	@FXML
	public void addNewElement(MouseEvent mouseEvent) {
		rootController.displayProjetEdit(new ProjetDto());
	}

	private void setInventaireIcone() {
		hasIncompleteInventaire = inventaireService.hasInventairesIncomplets();

		if (hasIncompleteInventaire) {
			customIcon.textBoxRemove(hasIncompleteInventaireIcn, 40);
		} else {
			customIcon.textBoxCheck(hasIncompleteInventaireIcn, 40);
		}
	}

	@FXML
	private void launchInventaire() {
		if (hasIncompleteInventaire) {
			displayInventaireService.batchInventaire(initializer);
			setInventaireIcone();
		}
	}
}
