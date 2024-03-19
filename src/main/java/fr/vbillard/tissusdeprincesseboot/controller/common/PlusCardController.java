package fr.vbillard.tissusdeprincesseboot.controller.common;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.projet.ProjetEditListElementController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(Utils.PROTOTYPE)
public class PlusCardController implements IController{

    protected StageInitializer initializer;
    protected FxData data;
    
    @FXML
    public HBox iconContainer;
    
    boolean isAddButton;
    
    private ProjetEditListElementController parent;
	
    @Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer; 
        this.data = data;
        
        parent = (ProjetEditListElementController)data.getParentController();
        
        setIcone(parent.isLocked() ? FontAwesomeIcon.LOCK : FontAwesomeIcon.PLUS_CIRCLE);
        
    }
    
    @FXML
    public void onClick(){
    	if (! parent.isLocked()) {
        parent.displaySelected(data);
    	} else {
    		Optional<ButtonType> result = ShowAlert.confirmation(initializer.getPrimaryStage(), "Débloquer",
    				"L'édition est déconseillée pour un projet au statut "+data.getProjet().getProjectStatus(),
    				"Souhaitez-vous débloquer l'édition de cet élément?");
    		
    		if(result.isPresent() && result.get() == ButtonType.OK){
    			parent.setLock(false);   			
            	setIcone(FontAwesomeIcon.PLUS_CIRCLE);
    		}
    	}
    }
    
    public void setIcone(FontAwesomeIcon icon) {
    	iconContainer.getChildren().clear();
    	FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
    	iconView.setSize("10em");
    	iconContainer.getChildren().add(iconView);

    }

}
