package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.ShowAlert;
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
public class FournitureUsedCardController implements IController {

	@FXML
	private Label longueur;
	@FXML
	private ImageView image;

	private final ImageService imageService;

	private final RootController rootController;

	private FournitureUsed fournitureUsed;

	private final FournitureService fournitureService;

	private FournitureDto fournitureDto;

    private ProjetEditListElementController parent;

    private StageInitializer initializer;

    private final FournitureUsedService fournitureUsedService;

	public FournitureUsedCardController(FournitureService fournitureService, FournitureUsedService fournitureUsedService
			, ImageService imageService, RootController rootController) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.fournitureUsedService = fournitureUsedService;
		this.fournitureService = fournitureService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		this.initializer = initializer;
		if (data == null || data.getFournitureUsed() == null) {
			throw new IllegalData();
		}
		fournitureUsed = data.getFournitureUsed();
        parent = (ProjetEditListElementController)data.getParentController();

		fournitureDto = fournitureService.convert(fournitureUsed.getFourniture());
		setCardContent();
	}

	private void setCardContent() {
		longueur.setText(fournitureUsed.getQuantite() + fournitureUsed.getRequis().getUnite().getAbbreviation());
		Optional<Photo> pictures = imageService.getImage(fournitureService.convert(fournitureDto));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		if (!parent.isLocked()) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Choisissez");
			alert.setHeaderText("Que souhaitez vous faire de " + ModelUtils.generateString(EntityToString.FOURNITURE_USED,
					Articles.DEMONSTRATIF));
			alert.initOwner(initializer.getPrimaryStage());

			ButtonType details = new ButtonType("Détails");
			ButtonType supprimer = new ButtonType("Supprimer");
			ButtonType annuler = new ButtonType("Anuler");

			alert.getButtonTypes().clear();

			alert.getButtonTypes().addAll(details, supprimer, annuler);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == details) {
				rootController.displayFournituresDetails(fournitureDto);
			} else if (option.get() == supprimer) {
				Optional<ButtonType> confirm = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.FOURNITURE_USED,
						fournitureUsed.toString());
				if (confirm.isPresent() && ButtonType.OK == confirm.get()) {
					fournitureUsedService.delete(fournitureUsed);
					parent.refresh();
				}
			
			} 

			
		}
	}
}