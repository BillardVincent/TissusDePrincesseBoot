package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

@Component
@Scope("prototype")
public class ListElementController implements IController{

    @FXML
    private Label ref;
    @FXML
    private Label titre;
    @FXML
    private Label description;
    @FXML
    private Label dimensions;

    protected StageInitializer initializer;

    private ModelMapper mapper;

    private ListElement element;

    public ListElementController (ModelMapper mapper){
        this.mapper = mapper;
    }
    
	protected void setElements() {
		ref.setText(element.getReference());
        titre.setText(element.getTitre());
        description.setText(element.getDescription());
        dimensions.setText(element.getDimensions());
	}
	
    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
        element = mapper.map(data[0], ListElement.class);
        setElements();

    }
}
