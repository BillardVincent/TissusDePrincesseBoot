package fr.vbillard.tissusdeprincesseboot.controller.patron;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class PatronCardController implements IController {

	@FXML
	private Label titre;
	@FXML
	private ImageView image;
	// @FXML
	// private Label referencePatronLabel;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private MaterialDesignIconView typeSupportIcn;

	private RootController rootController;
	private ImageService imageService;

	private ModelMapper mapper;

	private PatronDto patron;

	public PatronCardController(RootController rootController, ModelMapper mapper, ImageService imageService) {
		this.rootController = rootController;
		this.mapper = mapper;
		this.imageService = imageService;
	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		if (data == null || data.getPatron() == null) {
			throw new IllegalData();
		}
		patron = data.getPatron();
		setCardContent();

	}

	private void setCardContent() {
		// referencePatronLabel.setText(patron.getReference());
		marquePatronLabel.setText(patron.getMarque());
		modelPatronLabel.setText(patron.getModele());
		typeVetementPatronLabel.setText(patron.getTypeVetement());
		typeSupportIcn.setIcon(setIconTypeSupport(patron.getTypeSupport()));

		Optional<Photo> pictures = imageService.getImage(mapper.map(patron, Patron.class));
		image.setImage(imageService.imageOrDefault(pictures));
	}

	MaterialDesignIcon mat = null;

	private MaterialDesignIcon setIconTypeSupport(String typeSupport) {
		if (typeSupport == null) {
			return MaterialDesignIcon.HELP_CIRCLE;
		}
		SupportTypeEnum type = SupportTypeEnum.getEnum(typeSupport);
		switch (type) {
		case PDF:
			return MaterialDesignIcon.FILE_PDF;
		case PAPIER:
			return MaterialDesignIcon.BOOK_OPEN_VARIANT;
		case PROJECTION:
			return MaterialDesignIcon.THEATER;
		default:
			return MaterialDesignIcon.HELP_CIRCLE;

		}
	}

	@FXML
	public void showDetail() {
		rootController.displayPatronDetails(patron);
	}
}
