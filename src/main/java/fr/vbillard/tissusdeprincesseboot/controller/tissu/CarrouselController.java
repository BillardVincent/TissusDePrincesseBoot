package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IModalController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.CustomSpinner;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Component
public class CarrouselController implements IModalController{
	
	@FXML
	private Pane tissuPane;
	@FXML
	private Label initialLabel;
	@FXML
	private Label autreProjetLabel;
	@FXML
	private Label aloueLabel;
	@FXML
	private JFXTextField utiliseFiled;
	@FXML
	private JFXTextField restantField;
	@FXML
	private JFXCheckBox buildNewChuteBox;
	
	private Stage dialogStage;
	
	private TissuService tissuService;
	private TissuUsed tissuUsed;
	private StageInitializer stageInitializer;
	private ModelMapper mapper;
	private FxData result;
	
	public CarrouselController(ModelMapper mapper, TissuService tissuService, StageInitializer stageInitializer) {
		this.tissuService = tissuService;
		this.stageInitializer = stageInitializer;
		this.mapper = mapper;
	}
	
	@Override
	public FxData result() {
		return result;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		this.dialogStage = dialogStage;
		tissuUsed = data.getTissuUsed();
		
		Tissu tissu = tissuUsed.getTissu();
		int longueurUtilisee = tissuService.getLongueurUtilisee(tissu.getId());
		
		CustomSpinner.setSpinner(restantField);
		CustomSpinner.setSpinner(utiliseFiled);
		initialLabel.setText(String.valueOf(tissu.getLongueur()));
		autreProjetLabel.setText(String.valueOf(tissu.getLongueur() - tissu.getLongueurDisponible()));
		aloueLabel.setText(String.valueOf(tissuUsed.getLongueur()));
		utiliseFiled.setText(String.valueOf(tissuUsed.getLongueur()));
		restantField.setText(String.valueOf(tissu.getLongueurDisponible()));
		
		utiliseFiled.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (utiliseFiled.isFocused()) {
		    	int restantNewValue = tissu.getLongueur()- Integer.parseInt(utiliseFiled.getText());
		    	restantField.setText(String.valueOf(restantNewValue));
		    }
		});
		
		linkSpinnerValues(tissu, restantField, utiliseFiled);
		linkSpinnerValues(tissu, utiliseFiled, restantField);

		FxData cardData = new FxData();
		cardData.setTissu(mapper.map(tissu, TissuDto.class));
		tissuPane.getChildren().add(stageInitializer.displayPane(PathEnum.TISSUS_CARD, cardData));
	}

	private void linkSpinnerValues(Tissu tissu, JFXTextField observedField, JFXTextField linkedFiled) {
		observedField.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (observedField.isFocused()) {
		    	int utiliseNewValue = tissu.getLongueur() - Integer.parseInt(observedField.getText());
		    	if (utiliseNewValue<0) {
		    		utiliseNewValue = 0;
		    		observedField.setText(String.valueOf(tissu.getLongueur()));
		    	}
		    	
		    	linkedFiled.setText(String.valueOf(utiliseNewValue));
		    }
		});
	}
	
	
	@FXML
	public void validate() {
		result = new FxData();
		tissuUsed.setLongueur(Integer.parseInt(utiliseFiled.getText()));
		result.setTissuUsed(tissuUsed);
		result.setTissu(tissuService.convert(tissuUsed.getTissu()));
		dialogStage.close();
		
	}
	
	

}
