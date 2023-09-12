package fr.vbillard.tissusdeprincesseboot.controller.patron.detail;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.service.ProjetService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatronDetailController implements IController {

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
	private Label typeSupportPatronLabel;
	@FXML
	private HBox versionPane;

	private final RootController rootController;
	private StageInitializer initializer;
	private PatronDto patron;

	private final ProjetService projetService;
	private final PatronPictureHelper pictureUtils;
	private final PatronVersionService patronVersionservice;

	PatronDetailController(PatronPictureHelper pictureUtils, RootController rootController, PatronVersionService patronVersionservice,
			ProjetService projetService) {
		this.rootController = rootController;
		this.projetService = projetService;
		this.pictureUtils = pictureUtils;
		this.patronVersionservice = patronVersionservice;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getPatron() == null) {
			throw new IllegalData();
		}
		patron = data.getPatron();

		marquePatronLabel.setText(patron.getMarque());
		modelPatronLabel.setText(patron.getModele());
		typeVetementPatronLabel.setText(patron.getTypeVetement());
		typeSupportPatronLabel.setText(patron.getTypeSupport());

		// TODO patron version
		
		List<PatronVersionDto> patronVersionList = patronVersionservice.getDtoByPatronId(patron.getId());
		
		for (PatronVersionDto version : patronVersionList) {
			FxData versionData = new FxData();
			versionData.setPatronVersion(version);
			versionPane.getChildren().add(initializer.displayPane(PathEnum.PATRON_DETAIL_VERSION_DISPLAY, versionData));
		}


		/*
		for (TissuRequisDto t : patron.getTissusRequis()) {
			FxData fxData = new FxData();
			fxData.setTissuRequis(t);
			Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, fxData);
			listFournitures.getChildren().add(element);
		}
		
		for (FournitureRequiseDto t : patron.getFournituresRequises()) {
			FxData fxData = new FxData();
			fxData.setFournitureRequise(t);
			Pane element = initializer.displayPane(PathEnum.LIST_ELEMENT, fxData);
			listFournitures.getChildren().add(element);
		}

		 */
		
		pictureUtils.setPane(image, patron);

	}

	public void edit() {
		rootController.displayPatronEdit(patron);

	}

	
}
