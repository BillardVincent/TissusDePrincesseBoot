package fr.vbillard.tissusdeprincesseboot.controlers_v2.projet;

import java.io.ByteArrayInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.IController;
import fr.vbillard.tissusdeprincesseboot.controlers_v2.RootController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.modelmapper.ModelMapper;

@Component
@Scope("prototype")
public class TissuUsedCardController implements IController {

    @FXML
    public Label description;
    @FXML
    public Label titre;
    @FXML
    private Label longueur;
    @FXML
    private Label laize;
    @FXML
    private Label matiere;
    @FXML
    private Label type;
    @FXML
    private Label tissage;
    @FXML
    private Label poids;
    @FXML
    private Label unitePoids;
    @FXML
    private MaterialDesignIconView masse;
    @FXML
    private FontAwesomeIconView decati;
    @FXML
    private ImageView image;

    private ImageService imageService;

    private RootController rootController;

    private ModelMapper mapper;

    private TissuDto tissu;

    public TissuUsedCardController(ImageService imageService, RootController rootController, ModelMapper mapper) {
        this.imageService = imageService;
        this.rootController = rootController;
        this.mapper = mapper;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        if (data.length == 1 && data[0] instanceof TissuDto){
            tissu = (TissuDto)data[0];
            setCardContent();
        }
    }

    private void setCardContent() {
        description.setText(tissu.getDescription());
        longueur.setText(String.valueOf(tissu.getLongueur()));
        laize.setText(String.valueOf(tissu.getLaize()));
        matiere.setText(tissu.getMatiere());
        type.setText(tissu.getType());
        tissage.setText(tissu.getTissage());
        poids.setText(String.valueOf(tissu.getPoids()));
        unitePoids.setText(tissu.getUnitePoids());
        decati.setFill(tissu.isDecati() ? Constants.colorAdd : Constants.colorDelete);
        masse.setStyleClass(tissu.getPoids()> ConstantesMetier.MAX_POIDS_MOYEN ? "heavy-weight" :
                tissu.getPoids() > ConstantesMetier.MIN_POIDS_MOYEN ? "standard-weight" : "light-weight");
        Photo pictures = imageService.getImage(mapper.map(tissu, Tissu.class));
        image.setImage(imageService.imageOrDefault(pictures));

    }


    @FXML
    public void showDetail() {
        rootController.displayTissusDetails(tissu);
    }
}
