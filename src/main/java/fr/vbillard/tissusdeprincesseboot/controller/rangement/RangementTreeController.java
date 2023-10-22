package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TypeRangement;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRangement;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.service.*;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RangementTreeController implements IController {

    @FXML
    public JFXTreeView<RangementDto> treeView;
    @FXML
    public Pane detailPane;

    private StageInitializer initializer;

    private List<Integer> openPath;

    private final RangementTreeViewHelper rangementTreeViewHelper;
    private final RangementRootService rangementRootService;
    private final RangementService rangementService;
    private final TissuService tissuService;
    private final PatronService patronService;
    private final FournitureService fournitureService;

    public RangementTreeController(RangementTreeViewHelper rangementTreeViewHelper, RangementRootService rangementRootService, RangementService rangementService, TissuService tissuService, PatronService patronService, FournitureService fournitureService) {
        this.rangementTreeViewHelper = rangementTreeViewHelper;
        this.rangementRootService = rangementRootService;
        this.rangementService = rangementService;
        this.tissuService = tissuService;
        this.patronService = patronService;
        this.fournitureService = fournitureService;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        if (data != null && (data.getRangementRoot() != null)) {

            // TODO gérer spec
        }
        this.initializer = initializer;
        openPath = null;
        init();

    }

    private void init() {
        treeView = rangementTreeViewHelper.buildByRoot(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {
                    if (newValue == oldValue) {
                        treeView.getSelectionModel().select(treeView.getRoot());
                    }
                    displayEditPane(newValue);
                    if (newValue != null) getOpenedPath();
                }
        );

        openPreviousPath();


    }

    private void displayEditPane(TreeItem<RangementDto> newValue) {
        detailPane.getChildren().clear();
        if (newValue == null || newValue == treeView.getRoot()) {
            detailPane.getChildren().add(getEditPaneBase());
        } else if (newValue.getValue().getType() == TypeRangement.RANGEMENT) {
            detailPane.getChildren().add(getEditPane(newValue.getValue().getId()));
        } else {
            detailPane.getChildren().add(getEditRootPane(newValue.getValue().getId()));
        }

    }

    public VBox getEditRootPane(int id) {

        RangementRoot rr = rangementRootService.getById(id);

        Label nom = new Label("Nom du rangement : ");
        ClassCssUtils.setStyle(nom, ClassCssUtils.TITLE_ACC_1, true);
        JFXTextField nomField = new JFXTextField(rr.getNom());
        JFXButton addSubdivision = new JFXButton("Ajouter une subdivision");
        addSubdivision.onActionProperty().setValue(e -> {
            openPath.add(rangementTreeViewHelper.addSubdivision(rr));
            init();
        });

        JFXButton save = new JFXButton("Valider");
        save.onActionProperty().setValue(e -> {
            rr.setNom(nomField.getText());
            rangementRootService.saveOrUpdate(rr);
            init();
        });

        JFXButton delete = new JFXButton("Supprimer");
        delete.onActionProperty().setValue(e -> {
            Optional<ButtonType> validation = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.RANGEMENT, rr.getNom());
            if (validation.orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {
                rangementRootService.delete(rr.getId());
                init();
            }
        });

        HBox rangs = deplacerHbox(e -> minusRootRank(), e -> plusRootRank());

        VBox result = new VBox(nom, new HBox(nomField, save), rangs, addSubdivision, delete);
        result.setSpacing(10);

        return result;
    }

    public VBox getEditPane(int id) {

        Rangement r = rangementService.getById(id);

        Label nom = new Label("Nom du rangement : ");
        ClassCssUtils.setStyle(nom, ClassCssUtils.TITLE_ACC_1, true);
        JFXTextField nomField = new JFXTextField(r.getNom());
        JFXButton addSubdivision = new JFXButton("Ajouter une subdivision");
        addSubdivision.onActionProperty().setValue(e -> {
            openPath.add(rangementTreeViewHelper.addSubdivision(r));
            init();
        });

        JFXButton save = new JFXButton("Valider");
        save.onActionProperty().setValue(e -> {
            r.setNom(nomField.getText());
            rangementService.saveOrUpdate(r);
            init();
        });

        JFXButton delete = new JFXButton("Supprimer");
        delete.onActionProperty().setValue(e -> {
            Optional<ButtonType> validation = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.RANGEMENT, r.getNom());
            if (validation.orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {
                rangementService.delete(r.getId());
                init();
            }
        });

        HBox rangs = deplacerHbox(e -> minusRank(), e -> plusRank());
        VBox result = new VBox(nom, new HBox(nomField, save), rangs, addSubdivision);

        VBox contenu = new VBox();

        int tissuCount = tissuService.countByRangementId(id);

        if (tissuCount != 0){
            Label tissuLbl = new Label(tissuCount + "tissu" + (tissuCount>1?"s": Strings.EMPTY));
            JFXButton tissuBtn = new JFXButton("Voir");
            tissuBtn.onActionProperty().setValue(e -> {
                FxData data = new FxData();
                data.setSpecification(TissuSpecification.builder().rangement(r).archived(false).build());
                initializer.displayPane(PathEnum.TISSUS, data);
            });
            HBox tissuBox = new HBox(tissuLbl, tissuBtn);
            tissuBox.setSpacing(10);
            contenu.getChildren().add(tissuBox);
        }

        int fournitureCount = fournitureService.countByRangementId(id);

        if (fournitureCount != 0){
            Label fournitureLbl = new Label( fournitureCount + "fourniture" + (fournitureCount>1?"s": Strings.EMPTY));
            JFXButton fournitureBtn = new JFXButton("Voir");
            fournitureBtn.onActionProperty().setValue(e -> {
                FxData data = new FxData();
                data.setSpecification(FournitureSpecification.builder().rangement(r).archived(false).build());
                initializer.displayPane(PathEnum.FOURNITURES, data);
            });
            HBox fournitureBox = new HBox(fournitureLbl, fournitureBtn);
            fournitureBox.setSpacing(10);
            contenu.getChildren().add(fournitureBox);
        }

        int patronCount = patronService.countByRangementId(id);

        if (patronCount != 0){
            Label patronLbl = new Label( patronCount + "patron" + (patronCount>1?"s": Strings.EMPTY));
            JFXButton patronBtn = new JFXButton("Voir");
            patronBtn.onActionProperty().setValue(e -> {
                FxData data = new FxData();
                data.setSpecification(PatronSpecification.builder().rangement(r).archived(false).build());
                initializer.displayPane(PathEnum.PATRON_LIST, data);
            });
            HBox patronBox = new HBox(patronLbl, patronBtn);
            patronBox.setSpacing(10);
            contenu.getChildren().add(patronBox);
        }

        if (!contenu.getChildren().isEmpty()){
            contenu.getChildren().add(0, new Label("Contenu :"));
            result.getChildren().add(contenu);
        }

        result.getChildren().add(delete);
        result.setSpacing(10);

        return result;
    }

    private HBox deplacerHbox(EventHandler<ActionEvent> o, EventHandler<ActionEvent> o1) {
        Label rangLbl = new Label("Déplacer");
        JFXButton haut = new JFXButton("remonter");
        haut.onActionProperty().setValue(o);
        JFXButton bas = new JFXButton("descendre");
        bas.onActionProperty().setValue(o1);
        HBox rangs = new HBox(rangLbl, haut, bas);
        rangs.setSpacing(10);
        rangs.setAlignment(Pos.CENTER_LEFT);
        return rangs;
    }

    public VBox getEditPaneBase() {
        JFXButton newRoot = new JFXButton("Ajouter");
        newRoot.setOnAction( e -> {
            FxData result = initializer.displayModale(PathEnum.RANGEMENTS_MODALE, null, "Nouveau rangement");
            if (result != null && StringUtils.isNotBlank(result.getNom())){
                RangementRoot newRangment = new RangementRoot();
                newRangment.setNom(result.getNom());
                newRangment.setRang(rangementRootService.getAll().size());
                rangementRootService.saveOrUpdate(newRangment);
                init();
            }
        });

       return new VBox(newRoot);
    }

    void getOpenedPath() {
        TreeItem<RangementDto> selectedItem = treeView.getSelectionModel().getSelectedItem();

        TreeItem<RangementDto> rootItem = treeView.getRoot();


        if (selectedItem == null || rootItem == selectedItem) {
            openPath = null;
            return;
        }

        List<Integer> result = new ArrayList<>();
        for (TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
             item != null; item = item.getParent()) {
            if (item != rootItem) {
                result.add(0, item.getValue().getRang());
            }
        }
        openPath = result;
    }

    private void openPreviousPath() {
        if (CollectionUtils.isEmpty(openPath)) {
            treeView.getSelectionModel().select(treeView.getRoot());
            return;
        }

        LinkedList<Integer> fifo = new LinkedList<>(openPath);

        boolean hasChildren = !CollectionUtils.isEmpty(treeView.getRoot().getChildren());

        TreeItem<RangementDto> item = treeView.getRoot();

        boolean match = true;
        while (!fifo.isEmpty() && hasChildren && match) {
            int openedRank = fifo.removeFirst();
            match = false;
            for (TreeItem<RangementDto> child : item.getChildren()) {
                if (child.getValue().getRang() == openedRank) {
                    item = child;
                    match = true;
                    break;
                }
            }
        }
        treeView.getSelectionModel().select(item);
    }

    private void plusRank() {
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        List<Rangement> rangements = getRangementsAtSameLevel(item);

        if (item.getValue().getRang() == rangements.size()) {
            return;
        }
        changeRank(item, rangementService, rangements, 1);

    }

    private void minusRank() {
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        if (item.getValue().getRang() == 0) {
            return;
        }
        List<Rangement> rangements = getRangementsAtSameLevel(item);
        changeRank(item, rangementService, rangements, -1);

    }

    private List<Rangement> getRangementsAtSameLevel(TreeItem<RangementDto> item) {
        List<Rangement> rangements;
        if (item.getParent().getParent() == treeView.getRoot()) {
            rangements = rangementService.getByParentRoot(item.getParent().getValue().getId());
        } else {
            rangements = rangementService.getByParent(item.getParent().getValue().getId());
        }
        return rangements;
    }

    private void plusRootRank() {
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        List<RangementRoot> rangements = rangementRootService.getAll();
        if (item.getValue().getRang() == rangements.size()) {
            return;
        }
        changeRank(item, rangementRootService, rangements, 1);
    }

    private void minusRootRank() {
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        if (item.getValue().getRang() == 0) {
            return;
        }
        List<RangementRoot> rangements = rangementRootService.getAll();
        changeRank(item, rangementRootService, rangements, -1);

    }

    private <T extends AbstractRangement> void changeRank(TreeItem<RangementDto> item, AbstractService<T> service, List<T> rangements, int plusOrMinus1){
        for (T r : rangements) {
            if (item.getValue().getRang() == r.getRang()) {
                r.setRang(r.getRang() + plusOrMinus1);
                service.saveOrUpdate(r);
                openPath.set(openPath.size() - 1, r.getRang());
            } else if (item.getValue().getRang() + plusOrMinus1 == r.getRang()) {
                r.setRang(r.getRang() - plusOrMinus1);
                service.saveOrUpdate(r);
            }
        }

        init();
    }



    private void setRankAfterRemove(TreeItem<RangementDto> parent) {
       List<RangementDto> toRearange= parent.getChildren().stream().map(TreeItem::getValue).sorted(Comparator.comparingInt(RangementDto::getRang)).collect(Collectors.toList());
        for (int i = 0; i < toRearange.size()-1; i++) {
            if (i+1 != toRearange.get(i).getRang()){
                Rangement r  = rangementService.getById(toRearange.get(i).getId());
                r.setRang(i+1);
                rangementService.saveOrUpdate(r);
            }
        }
    }

    private void setRootRankAfterRemove(TreeItem<RangementDto> parent) {
        List<RangementDto> toRearange= parent.getChildren().stream().map(TreeItem::getValue).sorted(Comparator.comparingInt(RangementDto::getRang)).collect(Collectors.toList());
        for (int i = 0; i < toRearange.size()-1; i++) {
            if (i+1 != toRearange.get(i).getRang()){
                RangementRoot r  = rangementRootService.getById(toRearange.get(i).getId());
                r.setRang(i+1);
                rangementRootService.saveOrUpdate(r);
            }
        }
    }
}
