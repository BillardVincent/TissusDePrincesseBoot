package fr.vbillard.tissusdeprincesseboot.controlers_v2.patron;

import java.io.ByteArrayInputStream;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

@Component
@Scope("prototype")
public class PatronCardController implements IController{
	
    @FXML
    private Label titre;
    @FXML
    private ImageView image;
	//@FXML
	//private Label referencePatronLabel;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private Label descriptionPatronLabel;
	
	private RootController rootController;
	 
	private ModelMapper mapper;
	    
	private PatronDto patron;

	public PatronCardController(RootController rootController, ModelMapper mapper) {
        this.rootController = rootController;
        this.mapper = mapper;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        if (data.length == 1 && data[0] instanceof PatronDto){
        	patron = (PatronDto)data[0];
            setCardContent();
        }
    }

    private void setCardContent() {
    	//referencePatronLabel.setText(patron.getReference());
		marquePatronLabel.setText(patron.getMarque());
		modelPatronLabel.setText(patron.getModele());
		typeVetementPatronLabel.setText(patron.getTypeVetement());
    }


    @FXML
    public void showDetail() {
        rootController.displayPatronDetails(patron);
    }
}
