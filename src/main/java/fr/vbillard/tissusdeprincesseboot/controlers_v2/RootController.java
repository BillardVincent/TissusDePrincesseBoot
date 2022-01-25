package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@Component
public class RootController implements IController {

    @FXML
    private Pane mainWindow;
    @FXML
    private Pane selectedElement;
    @FXML
    private HBox tissuMenu;
    @FXML
    private HBox fournitureMenu;
    @FXML
    private HBox patronMenu;
    @FXML
    private HBox projetMenu;
    @FXML
    private Button deleteSelectedButton;
    
    private static final String SELECTED = "mainmenu-element-selected";
    
    private List<HBox> menuElements;
    private StageInitializer initializer;
	private TissuRequisDto tissuRequisSelected;
	private ProjetDto projetSelected;

    public RootController (){
    	deleteSelectedButton.setVisible(false);
    }

    @FXML
    public void displayTissus(){
    	beforeDisplay(tissuMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));
    }

    public void displayTissusDetails(TissuDto tissu){
    	beforeDisplay(tissuMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_DETAILS, tissu));
    }
    
    public void displayTissusEdit(TissuDto tissu){
    	beforeDisplay(tissuMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS_EDIT, tissu));
    }

    @FXML
    public void displayProjets(){
    	beforeDisplay(projetMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_LIST));
    }
    
    public void displayProjetDetails(ProjetDto projet){
    	beforeDisplay(projetMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_DETAILS, projet));
    }
    
    public void displayProjetEdit(ProjetDto projet){
    	beforeDisplay(projetMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PROJET_EDIT, projet));
    }

    @FXML
    public void displayPatrons(){
    	beforeDisplay(patronMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_LIST));
    }
    
    public void displayPatronDetails(PatronDto patron) {
    	beforeDisplay(patronMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAILS, patron));
    }
    
    public void displayPatronEdit(PatronDto patron) {
    	beforeDisplay(patronMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT, patron));
    }

    @FXML
    public void displayMatiereEdit(){
    	beforeDisplay(null);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.MATIERE));
    }

    @FXML
    public void displayTissageEdit(){
    	beforeDisplay(null);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSAGE));
    }

    @FXML
    public void displayTypeEdit(){
    	beforeDisplay(null);
    	mainWindow.getChildren().add(initializer.displayPane(PathEnum.TYPE_TISSU));
    }
    
    public void displaySelected(ProjetDto projet, TissuRequisDto tr ) {
    	tissuRequisSelected = tr;
    	projetSelected = projet;
    	selectedElement.getChildren().add(initializer.displayPane(PathEnum.TISSU_REQUIS_SELECTED, tr, projet));
    	beforeDisplay(tissuMenu);
        mainWindow.getChildren().add(initializer.displayPane(PathEnum.TISSUS));
    	deleteSelectedButton.setVisible(true);

    }
    
    @FXML
    public void deleteSelected() {
    	deleteSelectedButton.setVisible(false);
    	tissuRequisSelected = null;
    	projetSelected = null;
    	selectedElement.getChildren().clear();
    }
    
    private void beforeDisplay(HBox menuToSelect) {
        mainWindow.getChildren().clear();
        for(HBox hb : menuElements) {
        	if (hb != null && hb.getStyleClass() != null && !hb.getStyleClass().isEmpty()) {
            	hb.getStyleClass().removeIf(style -> style.equals(SELECTED));

        	}
        }
        if (menuToSelect != null ) {
            menuToSelect.getStyleClass().add(SELECTED);
        }

    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxDto... data) {
        this.initializer = initializer;
    	menuElements = Arrays.asList(tissuMenu,fournitureMenu, patronMenu, projetMenu);

    }

	public boolean hasTissuRequisSelected() {
		return tissuRequisSelected != null;
	}

	public void addToSelected(TissuDto tissu, int longueur) {
		TissuUsed tissuUsed = new TissuUsed();
		tissuUsed.setTissu(tissu);
		
		
	}
}
