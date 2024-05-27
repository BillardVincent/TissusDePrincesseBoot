package fr.vbillard.tissusdeprincesseboot.controller.projet;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
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
	private Optional<ButtonType> option;

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
		String abbreviation = fournitureUsed.getRequis().getUnite() != null ? fournitureUsed.getRequis().getUnite().getAbbreviation(): Strings.EMPTY;
		longueur.setText(fournitureUsed.getQuantite() + abbreviation);
		Optional<Photo> pictures = imageService.getImage(fournitureService.convert(fournitureDto));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		if (!parent.isLocked()) {

			Optional<ButtonType> option = ShowAlert.detailSuppressionAnnuler(initializer.getPrimaryStage(),
					EntityToString.FOURNITURE_USED);

			if (option.orElse(ButtonType.CANCEL) == ShowAlert.DETAILS) {
				rootController.displayFournituresDetails(fournitureDto);
			} else if (option.orElse(ButtonType.CANCEL) == ShowAlert.SUPPRIMER) {
				Optional<ButtonType> confirm = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.FOURNITURE_USED,
						fournitureUsed.toString());
				if (confirm.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					fournitureUsedService.delete(fournitureUsed);
					parent.refresh();
				}
			
			} 

			
		}
	}
}
