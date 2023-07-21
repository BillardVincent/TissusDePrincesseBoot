package fr.vbillard.tissusdeprincesseboot.controller.patron.detail;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

@Component
@Scope("prototype")
public class VersionDisplayController implements IController {
	
	@FXML
	private VBox tissuBox;
	@FXML
	private VBox fournitureBox;
	
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
		
		List<FournitureRequiseDto> fournitureRequises = fournitureRequisService.getAllFournitureRequiseDtoByVersion(data.getPatronVersion().getId());
		
		for (FournitureRequiseDto f : fournitureRequises) {
			Label label = new Label();
			label.setText(f.toString());
			fournitureBox.getChildren().add(label);
		}
		
		List<TissuRequisDto> tissusRequis = tissuRequisService.getAllTissuRequisDtoByPatron(data.getPatronVersion().getId());

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
