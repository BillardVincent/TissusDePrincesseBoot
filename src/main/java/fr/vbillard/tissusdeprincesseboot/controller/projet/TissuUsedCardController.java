package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class TissuUsedCardController implements IController {

	@FXML
	private Label longueur;
	@FXML
	private ImageView image;

	private final ImageService imageService;

	private final RootController rootController;

	private final ModelMapper mapper;

	private TissuUsed tissuUsed;

	private TissuDto tissu;
	
    private ProjetEditListElementController parent;
    
    private StageInitializer initializer;
    
    private final TissuUsedService tissuUsedService;

	public TissuUsedCardController(TissuUsedService tissuUsedService, ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
		this.tissuUsedService = tissuUsedService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getTissuUsed() == null) {
			throw new IllegalData();
		}
		tissuUsed = data.getTissuUsed();
        parent = (ProjetEditListElementController)data.getParentController();

		tissu = mapper.map(tissuUsed.getTissu(), TissuDto.class);
		setCardContent();
	}

	private void setCardContent() {
		longueur.setText(tissuUsed.getLongueur() + " cm");
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		if (!parent.isLocked()) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Choisissez");
			alert.setHeaderText("Que souhaitez vous faire de " + ModelUtils.generateString(EntityToString.TISSU_USED, Articles.DEMONSTRATIF));
			alert.initOwner(initializer.getPrimaryStage());

			ButtonType details = new ButtonType("Détails");
			ButtonType supprimer = new ButtonType("Supprimer");
			ButtonType annuler = new ButtonType("Annuler");

			alert.getButtonTypes().clear();

			alert.getButtonTypes().addAll(details, supprimer, annuler);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.isPresent() && option.get() == details) {
				rootController.displayTissusDetails(tissu);
			} else if (option.isPresent() && option.get() == supprimer) {
				Optional<ButtonType> confirm = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.TISSU_USED, tissuUsed.toString());
				if (confirm.isPresent() && ButtonType.OK == confirm.get()) {
					tissuUsedService.delete(tissuUsed);
					parent.refresh();
				}
			
			}
		}
	}
}
