package fr.vbillard.tissusdeprincesseboot.controller.projet;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

@Component
@Scope(Utils.PROTOTYPE)
public class TissuUsedCardController implements IController {

	@FXML
	private Label typeLbl;	
	@FXML
	private Label matiereLbl;	
	@FXML
	private Label tissageLbl;
	@FXML
	private Label longueurLbl;
	@FXML
	private Label laizeLbl;
	@FXML
	private Label poidsLbL;
	@FXML
	private HBox typeHbx;	
	@FXML
	private HBox tissageHbx;	
	@FXML
	private HBox matiereHbx;
	@FXML
	private HBox laizeHbx;
	@FXML
	private HBox poidsHbx;
	
	
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
		longueurLbl.setText(tissuUsed.getLongueur() + " cm");
		laizeLbl.setText(tissu.getLaize() + " cm");
		poidsLbL.setText(tissu.getPoids() + tissu.getUnitePoids());
		typeLbl.setText(ModelUtils.startWithMajuscule(tissu.getTypeTissu()));
		tissageLbl.setText(tissu.getTissage());
		matiereLbl.setText(tissu.getMatiere());
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		if (!parent.isLocked()) {
			
			Optional<ButtonType> option = ShowAlert.detailSuppressionAnnuler(initializer.getPrimaryStage(),
					EntityToString.TISSU_USED);

			if (option.orElse(ButtonType.CANCEL)  == ShowAlert.DETAILS) {
				rootController.displayTissusDetails(tissu);
			} else if (option.orElse(ButtonType.CANCEL) == ShowAlert.SUPPRIMER) {
				Optional<ButtonType> confirm = ShowAlert.suppression(initializer.getPrimaryStage(), EntityToString.TISSU_USED, tissuUsed.toString());
				if (confirm.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					tissuUsedService.delete(tissuUsed);
					parent.refresh();
				}
			
			}
		}
	}
}
