package fr.vbillard.tissusdeprincesseboot.controller.rangement;

import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.model.RangementRootDemat;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

@Component
public class RangementTreeController implements IController {

    @FXML
    public JFXTreeView<RangementDto> treeView;
    @FXML
    public Pane detailPane;

    private RangementRoot root;
    private RangementRootDemat rootDemat;

    private final RangementService rangementService;

    public RangementTreeController(RangementService rangementService) {
        this.rangementService = rangementService;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        if (data == null || (data.getRangementRoot() == null) == (data.getRangementRootDemat() == null)){
            throw new IllegalArgumentException();
        }
        root = data.getRangementRoot();
        rootDemat = data.getRangementRootDemat();

        if (root != null){
            treeView = rangementService.buildByRoot(root);

        } else {

        }

    }

    public void displayPieces(MouseEvent mouseEvent) {
    }

    public void displayMobile(MouseEvent mouseEvent) {
    }

    public void displayDemat(MouseEvent mouseEvent) {
    }
}
