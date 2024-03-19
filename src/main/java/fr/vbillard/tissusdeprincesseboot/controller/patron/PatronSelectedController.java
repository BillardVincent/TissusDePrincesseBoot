package fr.vbillard.tissusdeprincesseboot.controller.patron;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(Utils.PROTOTYPE)
public class PatronSelectedController implements IController {

    @FXML
    public Label titre;
    @FXML
    public ImageView image;

    @FXML
    public Label marquePatronLabel;
    @FXML
    public Label modelPatronLabel;
    @FXML
    public Label typeVetementPatronLabel;
    @FXML
    public MaterialDesignIconView typeSupportIcn;

    private ImageService imageService;

    private ModelMapper mapper;

    private PatronDto patron;

    public PatronSelectedController(ModelMapper mapper, ImageService imageService) {
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

}

