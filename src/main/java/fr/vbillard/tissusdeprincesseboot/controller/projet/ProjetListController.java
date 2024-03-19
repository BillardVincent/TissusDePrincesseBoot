package fr.vbillard.tissusdeprincesseboot.controller.projet;

import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.DisplayInventaireService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ViewListController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomIcon;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.ProjetSpecification;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.InventaireService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProjetListController extends ViewListController {

    boolean hasIncompleteInventaire;

    @FXML
    public Pane hasIncompleteInventaireIcn;

    private final ProjetService projetService;
    private final RootController rootController;
    private final DisplayInventaireService displayInventaireService;
    private final InventaireService inventaireService;
    private final CustomIcon customIcon;

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
        if (specification instanceof ProjetSpecification s) {
            spec = s;
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
        hasIncompleteInventaireIcn.getChildren().clear();
        if (hasIncompleteInventaire) {
            hasIncompleteInventaireIcn.getChildren().add(customIcon.textBoxRemove(40));
        } else {
            hasIncompleteInventaireIcn.getChildren().add(customIcon.textBoxCheck(40));
        }
    }

    @FXML
    public void launchInventaire() {
        if (hasIncompleteInventaire) {
            displayInventaireService.batchInventaire(initializer);
            setInventaireIcone();
        }
    }
}
