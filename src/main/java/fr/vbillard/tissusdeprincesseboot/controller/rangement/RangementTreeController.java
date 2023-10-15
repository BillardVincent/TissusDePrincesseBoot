package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.model.RangementRootDemat;
import fr.vbillard.tissusdeprincesseboot.service.RangementRootService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    private RangementRoot root;
    private RangementRootDemat rootDemat;

    public RangementTreeController(RangementTreeViewHelper rangementTreeViewHelper, RangementRootService rangementRootService, RangementService rangementService) {
        this.rangementTreeViewHelper = rangementTreeViewHelper;
        this.rangementRootService = rangementRootService;
        this.rangementService = rangementService;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        if (data == null || (data.getRangementRoot() == null) == (data.getRangementRootDemat() == null)){
            throw new IllegalArgumentException();
        }
        root = data.getRangementRoot();
        rootDemat = data.getRangementRootDemat();
        this.initializer = initializer;

        init();

    }

    private void init() {
        if (root != null){
            treeView = rangementTreeViewHelper.buildByRoot(root, treeView);
        } else {
           treeView = rangementTreeViewHelper.buildByRoot(rootDemat, treeView);
        }

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue == oldValue){
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
        if(newValue == treeView.getRoot()){
            detailPane.getChildren().add(getEditRootPane(newValue.getValue().getId()));
        } else if (newValue != null){
            detailPane.getChildren().add(getEditPane(newValue.getValue().getId()));

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
            if (validation.orElse(ButtonType.CANCEL).equals(ButtonType.OK)){
                rangementRootService.delete(rr.getId());
                init();
            }
        });

        VBox result = new VBox(nom, new HBox(nomField, save), addSubdivision, delete);
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
            if (validation.orElse(ButtonType.CANCEL).equals(ButtonType.OK)){
                rangementService.delete(r.getId());
                init();
            }
        });

        Label rangLbl = new Label("Déplacer");
        JFXButton haut= new JFXButton("remonter");
        haut.onActionProperty().setValue(e ->  minusRank());
        JFXButton bas= new JFXButton("descendre");
        bas.onActionProperty().setValue(e ->  plusRank());
        HBox rangs = new HBox(rangLbl, haut, bas);
        rangs.setSpacing(10);

        VBox result = new VBox(nom, new HBox(nomField, save), rangs, addSubdivision, delete);
        result.setSpacing(10);

        return result;
    }

    
    void getOpenedPath(){
        TreeItem<RangementDto> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null){
            openPath = new ArrayList<>();
        }

        TreeItem<RangementDto> rootItem = treeView.getRoot();

        if (rootItem == selectedItem){
            openPath =  List.of(0);
        }

        List<Integer> result = new ArrayList<>();
        for (TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
             item != null ; item = item.getParent()) {

            result.add(0, item.getValue().getRang());
        }
        openPath =  result;
    }

    private void openPreviousPath(){
        if (CollectionUtils.isEmpty(openPath)){
            return;
        }

        if (openPath.size() == 1){
            treeView.getSelectionModel().select(treeView.getRoot());
            return;
        }

        LinkedList<Integer> fifo = new LinkedList<>(openPath);

        boolean hasChildren = !CollectionUtils.isEmpty(treeView.getRoot().getChildren());

        TreeItem<RangementDto> item = treeView.getRoot();

        boolean match = true;
        // suppression du 0 de la root
        fifo.removeFirst();

        while (! fifo.isEmpty() && hasChildren && match){
            int openedRank = fifo.removeFirst();
            match = false;
            for (TreeItem<RangementDto> child : item.getChildren()){
                if (child.getValue().getRang() == openedRank){
                    item = child;
                    match = true;
                    break;
                }
            }
        }
        treeView.getSelectionModel().select(item);
    }

    private void plusRank(){
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        List<Rangement> rangements;
        if (item.getParent().getParent() == null){
            rangements = rangementService.getByParentRoot(item.getParent().getValue().getId());
        } else {
            rangements = rangementService.getByParent(item.getParent().getValue().getId());
        }

        if (item.getValue().getRang() == rangements.size()){
            return ;
        }
        for (Rangement r : rangements){
            if (item.getValue().getRang() == r.getRang()){
                r.setRang(r.getRang() +1);
                rangementService.saveOrUpdate(r);
                openPath.set(openPath.size()-1, r.getRang());
            } else if (item.getValue().getRang() +1 == r.getRang()){
                r.setRang(r.getRang() -1);
                rangementService.saveOrUpdate(r);
            }
        }

        init();
    }

    private void minusRank(){
        TreeItem<RangementDto> item = treeView.getSelectionModel().getSelectedItem();
        if (item.getValue().getRang() == 0){
            return;
        }
        List<Rangement> rangements;
        if (item.getParent().getParent() == null){
            rangements = rangementService.getByParentRoot(item.getParent().getValue().getId());
        } else {
            rangements = rangementService.getByParent(item.getParent().getValue().getId());
        }
        for (Rangement r : rangements){
            if (item.getValue().getRang() == r.getRang()){
                r.setRang(r.getRang() -1);
                rangementService.saveOrUpdate(r);
                openPath.set(openPath.size()-1, r.getRang());
            } else if (item.getValue().getRang() -1 == r.getRang()){
                r.setRang(r.getRang() +1);
                rangementService.saveOrUpdate(r);
            }
        }

    init();
    }


    private void setRankAfterRemove(TreeItem<RangementDto> parent){

    }
}
