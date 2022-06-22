package fr.vbillard.tissusdeprincesseboot.controller.patron;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ListElement;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
@Scope("prototype")
public class ListElementController implements IController {

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

	public ListElementController(ModelMapper mapper) {
		this.mapper = mapper;
	}

	protected void setElements() {
		ref.setText(element.getReference());
		titre.setText(element.getTitre());
		description.setText(element.getDescription());
		dimensions.setText(element.getDimensions());
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data.getTissuRequis() != null) {
			element = mapper.map(data.getTissuRequis(), ListElement.class);

		} else if (data.getTissuUsed() != null) {
			element = mapper.map(data.getTissuUsed(), ListElement.class);

		}

		setElements();
	}

}
