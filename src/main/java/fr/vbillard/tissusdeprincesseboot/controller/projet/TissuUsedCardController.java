package fr.vbillard.tissusdeprincesseboot.controller.projet;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.IController;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Optional;

@Component
@Scope("prototype")
public class TissuUsedCardController implements IController {

	
	public Label titre;
	@FXML
	private Label longueur;
	
	@FXML
	private ImageView image;

	private ImageService imageService;

	private RootController rootController;

	private ModelMapper mapper;

	private TissuUsed tissuUsed;
	
	private TissuDto tissu;

	public TissuUsedCardController(ImageService imageService, RootController rootController, ModelMapper mapper) {
		this.imageService = imageService;
		this.rootController = rootController;
		this.mapper = mapper;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getTissuUsed() == null) {
			throw new IllegalData();
		}
		tissuUsed = data.getTissuUsed();
		tissu = mapper.map(tissuUsed.getTissu(), TissuDto.class);
		setCardContent();
	}

	private void setCardContent() {
		longueur.setText(String.valueOf(tissuUsed.getLongueur()));
		Optional<Photo> pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
		image.setImage(imageService.imageOrDefault(pictures));

	}

	@FXML
	public void showDetail() {
		rootController.displayTissusDetails(tissu);
	}
}
