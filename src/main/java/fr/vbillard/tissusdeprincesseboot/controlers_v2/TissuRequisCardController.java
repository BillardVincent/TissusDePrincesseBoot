package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class TissuRequisCardController implements IController {
	
	@FXML
	private Label longueurLabel;
	@FXML
	private Label laizeLabel;
	@FXML
	private Label gammePoidsLabel;
	@FXML
	private Label variantsLabel;
	
	private StageInitializer initializer;
	private TissuRequisDto tissuRequis;
	
	private RootController rootController;
	
	

	public TissuRequisCardController(RootController rootController) {
		this.rootController = rootController;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, Object... data) {
		this.initializer = initializer;
		if (data.length == 1 && data[0] instanceof TissuRequisDto) {
			tissuRequis = (TissuRequisDto) data[0];
			if (tissuRequis != null) {
				longueurLabel.setText(Integer.toString(tissuRequis.getLongueur()));
				laizeLabel.setText(Integer.toString(tissuRequis.getLaize()));
				gammePoidsLabel.setText(tissuRequis.getGammePoids());
				

			} else {
				longueurLabel.setText("");
				laizeLabel.setText("");
				gammePoidsLabel.setText("");
				variantsLabel.setText("");
			}
			
			variantsLabel.setText(StringUtils.join(tissuRequis.getVariant(), " - "));
		}
		setPane();
	}

	private void setPane() {
		// TODO Auto-generated method stub

	}
	
	@FXML
	private void chooseTissuSelected() {
		rootController.displaySelected(tissuRequis);
	}
}
