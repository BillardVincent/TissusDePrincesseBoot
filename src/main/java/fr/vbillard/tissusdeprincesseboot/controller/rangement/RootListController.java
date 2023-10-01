package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXButton;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.model.RangementRootDemat;
import fr.vbillard.tissusdeprincesseboot.service.RangementRootDematService;
import fr.vbillard.tissusdeprincesseboot.service.RangementRootService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RootListController implements IController {
    @FXML
    public VBox rootPane;

    private StageInitializer initializer;
    private RangementRootService rootService;
    private RangementRootDematService rootDematService;

    public RootListController(RangementRootService rootService, RangementRootDematService rootDematService) {
        this.rootService = rootService;
        this.rootDematService = rootDematService;
    }


    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;

        init();
    }

    private void init() {
        rootPane.getChildren().clear();

        List<RangementRoot> rootList = rootService.getAll();
        List<RangementRootDemat> rootDematList = rootDematService.getAll();

        Label titre = new Label("Rangements physiques");
        titre.getStyleClass().add(ClassCssUtils.TITLE_MAIN_PANE);
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        for (RangementRoot rr : rootList) {
            Label titreCard = new Label(rr.getNom());
            VBox box = new VBox(titreCard);
            box.setPadding(new Insets(10, 10, 10,10));
            box.getStyleClass().addAll("global", "card", ClassCssUtils.CLICKABLE);
            Pane p = new Pane(box);
            box.setOnMouseClicked(e -> {
                FxData data2 = new FxData();
                data2.setRangementRoot(rr);
                initializer.displayPane(PathEnum.RANGEMENT_TREE, data2);
            });

            tilePane.getChildren().add(p);
        }
        JFXButton newRoot = new JFXButton("Ajouter");
        newRoot.setOnAction( e -> {
            FxData result = initializer.displayModale(PathEnum.RANGEMENTS_MODALE, null, "Nouveau rangement");
            if (result != null && StringUtils.isNotBlank(result.getNom())){
                RangementRoot newRangment = new RangementRoot();
                newRangment.setNom(result.getNom());
                rootService.saveOrUpdate(newRangment);
                init();
            }

        });

        VBox rootContent = new VBox(titre, tilePane, newRoot);
        rootContent.getStyleClass().add(ClassCssUtils.LIGHT_BACKGROUND);
        rootContent.setSpacing(20);
        rootContent.setPadding(new Insets(20));

        //////////////////////////////////////////////

        Label titreDemat = new Label("Rangements dématérialisés");
        titreDemat.getStyleClass().add(ClassCssUtils.TITLE_MAIN_PANE);
        TilePane tilePaneDemat = new TilePane();
        tilePaneDemat.setVgap(10);
        tilePaneDemat.setHgap(10);
        for (RangementRootDemat rr : rootDematList) {
            Label titreCard = new Label(rr.getNom());
            VBox box = new VBox(titreCard);
            box.setPadding(new Insets(10, 10, 10,10));
            box.getStyleClass().addAll("global", "card", ClassCssUtils.CLICKABLE);
            Pane p = new Pane(box);
            box.setOnMouseClicked(e -> {
                FxData data2 = new FxData();
                data2.setRangementRootDemat(rr);
                initializer.displayPane(PathEnum.RANGEMENT_TREE, data2);
            });

            tilePaneDemat.getChildren().add(p);
        }
        JFXButton newRootDemat = new JFXButton("Ajouter");
        newRootDemat.setOnAction( e -> {
            FxData result = initializer.displayModale(PathEnum.RANGEMENTS_MODALE, null, "Nouveau rangement dématérialisé");
            if (result != null && StringUtils.isNotBlank(result.getNom())){
                RangementRootDemat newRangment = new RangementRootDemat();
                newRangment.setNom(result.getNom());
                rootDematService.saveOrUpdate(newRangment);
                init();
            }

        });

        VBox rootContentDemat = new VBox(titreDemat, tilePaneDemat, newRootDemat);
        rootContentDemat.getStyleClass().add(ClassCssUtils.LIGHT_BACKGROUND);
        rootContentDemat.setSpacing(20);
        rootContentDemat.setPadding(new Insets(20));


        rootPane.getChildren().addAll(rootContent, rootContentDemat);
        rootPane.setSpacing(50);

    }
}
