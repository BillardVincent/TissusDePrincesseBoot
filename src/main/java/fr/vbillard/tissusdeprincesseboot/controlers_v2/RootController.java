package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

@Component
public class RootController implements IController {

    @FXML
    private Pane mainWindow;

    private StageInitializer initializer;

    public RootController (){

    }

    @FXML
    public void displayTissus(){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));
    }

    @FXML
    public void displayTissusDetails(TissuDto tissu){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_DETAILS, tissu));
    }
    
    @FXML
    public void displayTissusEdit(TissuDto tissu){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_EDIT, tissu));
    }

    @FXML
    public void displayProjets(){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_LIST));
    }
    
    @FXML
    public void displayProjetDetails(ProjetDto projet){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_DETAILS, projet));
    }
    
    @FXML
    public void displayProjetEdit(ProjetDto projet){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_EDIT, projet));
    }

    @FXML
    public void displayPatrons(){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_LIST));
    }
    
    @FXML
    public void displayPatronDetails(PatronDto patron) {
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAILS, patron));
    }
    
    @FXML
    public void displayPatronEdit(PatronDto patron) {
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT, patron));
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
    }
}
