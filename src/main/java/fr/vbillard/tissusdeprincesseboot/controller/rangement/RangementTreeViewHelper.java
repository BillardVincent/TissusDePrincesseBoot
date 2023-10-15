package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.model.RangementRootDemat;
import fr.vbillard.tissusdeprincesseboot.service.RangementRootService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
public class RangementTreeViewHelper {

    private final RangementRootService rangementRootService;
    private final RangementService rangementService;

    public RangementTreeViewHelper(RangementRootService rangementRootService, RangementService rangementService) {
        this.rangementRootService = rangementRootService;
        this.rangementService = rangementService;
    }


    @Transactional
    public JFXTreeView<RangementDto> buildByRoot(RangementRoot root, JFXTreeView<RangementDto> treeView){

        RangementDto commonRoot = new RangementDto(0, "Tous");
        RangementDto physicalRoot = new RangementDto(1, "Lieux de rangement");
        RangementDto dematerializedRoot = new RangementDto(2, "Supports dématérialisés");



        List<RangementRoot> rrList = rangementRootService.getAll();

        RangementRoot rr = rangementRootService.getById(root.getId());
        TreeItem<RangementDto> rootItem = new TreeItem<>(rangementRootService.convert(rr));

        for (Rangement r : rr.getSubdivision()){
            rootItem.getChildren().add(buildItem(r));
        }

        treeView.setRoot(rootItem);


        return treeView;
    }

    /**
     * Methode recursive
     * @param rangement élément à convertir
     * @return TreeItem<RangementDto>
     */
    private TreeItem<RangementDto> buildItem(Rangement rangement) {
        TreeItem<RangementDto> item = new TreeItem<>(rangementService.convert(rangement));
        rangement.getSubdivision().sort(Comparator.comparingInt(Rangement::getRang));
        for (Rangement r : rangement.getSubdivision()){
            item.getChildren().add(buildItem(r));
        }
        return item;

    }

    @Transactional
    public JFXTreeView<RangementDto> buildByRoot(RangementRootDemat root, JFXTreeView<RangementDto> treeView){

        // TODO


        return treeView;
    }

    /**
     * Ajoute une subdivision à un élément racine. nécessite d'etre dans une transaction
     * @param rr rangement "racine" auquel ajouter la subdivision
     */
    @Transactional
    public int addSubdivision(RangementRoot rr) {
        RangementRoot rr2 = rangementRootService.getById(rr.getId());
        Rangement r = new Rangement();
        r.setNom("Nouveau rangement");
        r.setConteneurRoot(rr2);
        r.setRang(rr2.getSubdivision().size());
        rangementService.saveOrUpdate(r);

        return r.getRang();
    }

    /**
     * Ajoute une subdivision à un élément. nécessite d'etre dans une transaction
     * @param parentLazy rangement "parent" auquel ajouter la subdivision
     */
    @Transactional
    public int addSubdivision(Rangement parentLazy) {
        Rangement parent = rangementService.getById(parentLazy.getId());
        Rangement r = new Rangement();
        r.setNom("Nouveau rangement");
        r.setConteneur(parent);
        r.setRang(parent.getSubdivision().size());
        rangementService.saveOrUpdate(r);

        return r.getRang();

    }
}
