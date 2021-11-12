package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
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
    public void displayProjets(){
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJETS));
    }

    @Override
    public void setStageInitializer(StageInitializer initializer) {
        this.initializer = initializer;
    }
}
