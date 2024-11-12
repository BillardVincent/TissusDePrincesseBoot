package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FournitureSelectedController implements IController {

    @FXML
    public Label nom;
    @FXML
    public Label nombreEtUnite;
    @FXML
    public Label type;
    @FXML
    public ImageView image;

    private final ImageService imageService;

    private final RootController rootController;

    private final FournitureService fournitureService;

    private FournitureDto fourniture;

    public FournitureSelectedController(ImageService imageService, RootController rootController,
            FournitureService fournitureService) {
        this.imageService = imageService;
        this.rootController = rootController;
        this.fournitureService = fournitureService;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        if (data == null || data.getFourniture() == null) {
            throw new IllegalData();
        }
        fourniture = data.getFourniture();
        setCardContent();

    }

    private void setCardContent() {
        nombreEtUnite.setText(safePropertyToString(fourniture.getQuantiteDisponibleProperty()) +
                safePropertyToString(fourniture.getUniteShortProperty()));
        type.setText(safePropertyToString(fourniture.getTypeNameProperty()));
        nom.setText(safePropertyToString(fourniture.getNomProperty()));
        Optional<Photo> pictures = imageService.getImage(fournitureService.convert(fourniture));
        image.setImage(imageService.imageOrDefault(pictures.orElse(null)));

    }


}
