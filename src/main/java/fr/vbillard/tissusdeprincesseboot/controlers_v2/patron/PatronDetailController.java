package fr.vbillard.tissusdeprincesseboot.controlers_v2.patron;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.utils.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@Component
public class PatronDetailController implements IController{
	
	@FXML
    public Label titre;
    @FXML
    private ImageView image;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private Label descriptionPatronLabel;
	@FXML
	private ScrollPane listFournitures;
	
	private RootController rootController;
	private StageInitializer initializer;
	private PatronDto patron;
	
	private ModelMapper mapper;

    PatronDetailController(ModelMapper mapper, RootController rootController){
    	this.mapper = mapper;
    	this.rootController = rootController;
    }
    
	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		this.initializer = initializer;
        if (data.length == 1 && data[0] instanceof PatronDto){
            patron = (PatronDto) data[0];
            
            marquePatronLabel.setText(patron.getMarque());
    		modelPatronLabel.setText(patron.getModele());
    		typeVetementPatronLabel.setText(patron.getTypeVetement());

			VBox root = new VBox();
			root.setSpacing(10);

            for (TissuRequisDto t : patron.getTissusRequis()){
                Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, t);
                root.getChildren().add(element);
            }
			listFournitures.setContent(root);

		}
	}
	
    public void edit(){
        rootController.displayPatronEdit(patron);

    }
    
       
	/*
	txtInput.setEditable(false);
	txtInput.setMouseTransparent(true);
	txtInput.setFocusTraversable(false);
	*/
}
