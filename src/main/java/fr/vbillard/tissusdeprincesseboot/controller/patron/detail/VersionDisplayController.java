package fr.vbillard.tissusdeprincesseboot.controller.patron.detail;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils.TITLE_ACC_3;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils.setStyle;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.DECIMAL_FORMAT;

@Component
@Scope(Utils.PROTOTYPE)
public class VersionDisplayController implements IController {
	
	@FXML
	private VBox tissuBox;
	@FXML
	private VBox fournitureBox;
	@FXML
	private Label nomVersion;
	
	private PatronVersionDto version;

	private final RootController rootController;
	private StageInitializer initializer;
	private final FournitureRequiseService fournitureRequisService;
	private final TissuRequisService tissuRequisService;
	private final ProjetService projetService;


	VersionDisplayController(RootController rootController, FournitureRequiseService fournitureRequisService, 
			TissuRequisService tissuRequisService, ProjetService projetService) {
		this.rootController = rootController;
		this.fournitureRequisService = fournitureRequisService;
		this.tissuRequisService = tissuRequisService;
		this.projetService = projetService;

	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getPatronVersion() == null) {
			throw new IllegalData();
		}
		
		version = data.getPatronVersion();
		
		nomVersion.setText(version.getNom());
		
		List<FournitureRequiseDto> fournitureRequises =
				fournitureRequisService.getAllFournitureRequiseDtoByVersion(data.getPatronVersion().getId());
		
		for (FournitureRequiseDto f : fournitureRequises) {
			Label type = new Label();
			type.setText(f.getType().getValue());
			setStyle(type, TITLE_ACC_3, true);

			Label description = new Label (f.getType().getIntitulePrincipale() + " : " +
					DECIMAL_FORMAT.format(f.getQuantite()) + " " + f.getUnite().getAbbreviation());

			HBox hBox = new HBox(type, description);
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.setSpacing(5);
			fournitureBox.getChildren().add(hBox);
		}
		
		List<TissuRequisDto> tissusRequis =
				tissuRequisService.getAllTissuRequisDtoByPatron(data.getPatronVersion().getId());

		for (int i = 0; i <  tissusRequis.size(); i++) {
			TissuRequisDto t = tissusRequis.get(i);
			FxData fData = new FxData();
			fData.setTissuRequis(t);
			if (tissusRequis.size()>1) {
				fData.setRank(i);
			}
			tissuBox.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAIL_TISSU_DISPLAY, fData));
		}
		
	}
	
	@FXML
	public void handleStartProject() {
		ProjetDto projet = projetService.startNewProjet(version.getId());
		rootController.displayProjetEdit(projet);
	}

}
