package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controlers.IController;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.fxCustomElements.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TypeTissuService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.RowConstraints;
import org.modelmapper.ModelMapper;

@Component
public class TissuDetailController implements IController {
    @FXML
    public ToggleButton decatiField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField lieuDachatField;
    @FXML
    public Spinner<Integer> poidsField;
    @FXML
    public ChoiceBox<String> unitePoidsField;
    @FXML
    public Spinner<Integer> laizeField;
    @FXML
    public Spinner<Integer> longueurField;
    @FXML
    public ToggleButton chuteField;
    @FXML
    public Button addTypeButton;
    @FXML
    public ChoiceBox<String> typeField;
    @FXML
    public ChoiceBox<String> matiereField;
    @FXML
    public Button addMatiereButton;
    @FXML
    public ChoiceBox<String> tissageField;
    @FXML
    public Button addTissageButton;
    @FXML
    public TextField referenceField;
    @FXML
    public Button generateReferenceButton;
    @FXML
    public Label ancienneValeurLabel;
    @FXML
    public Label ancienneValeurInfo;
    @FXML
    public Label consommeLabel;
    @FXML
    public Label consommeIndo;
    public RowConstraints ancienneValeurRow;
    public RowConstraints consommeRow;

    private int longueur;
    private int laize;
    private int poids;

    StageInitializer initializer;

    TissuDto tissu;

    ModelMapper mapper;
    TypeTissuService typeTissuService;
    MatiereService matiereService;
    TissageService tissageService;

    public TissuDetailController(ModelMapper mapper, TypeTissuService typeTissuService, MatiereService matiereService, TissageService tissageService) {
        this.mapper = mapper;
        this.typeTissuService = typeTissuService;
        this.matiereService = matiereService;
        this.tissageService = tissageService;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, Object... data) {
        this.initializer = initializer;
        if (data.length == 1 && data[0] instanceof TissuDto){
            tissu = (TissuDto) data[0];
            if (tissu == null || tissu.getChuteProperty() == null) {
                tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, typeTissuService.getAll().get(0), 0,
                        UnitePoids.NON_RENSEIGNE, false, "", null, false), TissuDto.class);
            }

            longueurField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
                    tissu.getLongueurProperty() == null ? 0 : tissu.getLongueur()));
            laizeField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
                    tissu.getLaizeProperty() == null ? 0 : tissu.getLaize()));
            poidsField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
                    tissu.getPoidseProperty() == null ? 0 : tissu.getPoids()));
            referenceField.setText(tissu.getReferenceProperty() == null ? "" : tissu.getReference());
            descriptionField.setText(tissu.getDescriptionProperty() == null ? "" : tissu.getDescription());
            decatiField.setSelected(tissu.getDecatiProperty() != null && tissu.isDecati());
            lieuDachatField.setText(tissu.getLieuAchatProperty() == null ? "" : tissu.getLieuAchat());
            chuteField.setSelected(tissu.getChuteProperty() != null && tissu.isChute());

            unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
            unitePoidsField.setValue(
                    tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

            typeField.setItems(FXCollections.observableArrayList(
                    typeTissuService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            typeField.setValue(tissu.getTypeProperty() == null ? "" : tissu.getType());

            matiereField.setItems(FXCollections.observableArrayList(
                    matiereService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            matiereField.setValue(tissu.getMatiereProperty() == null ? "" : tissu.getMatiere());

            tissageField.setItems(FXCollections.observableArrayList(
                    tissageService.getAll().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList())));
            tissageField.setValue(tissu.getTissageProperty() == null ? "" : tissu.getTissage());

            longueur = longueurField.getValue();
            laize = laizeField.getValue();
            poids = poidsField.getValue();        }
    }

    @FXML
    private void initialize() {

        addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
        generateReferenceButton.setGraphic(magicIcon);
        generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

        longueurField.valueProperty().addListener((obs, oldValue, newValue) -> longueur = newValue);
        longueurField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                longueurField.increment(0); // won't change value, but will commit editor
                longueur = longueurField.getValue();
            }
        });
        laizeField.valueProperty().addListener((obs, oldValue, newValue) -> laize = newValue);
        laizeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                laizeField.increment(0); // won't change value, but will commit editor
                laize = laizeField.getValue();
            }
        });
        poidsField.valueProperty().addListener((obs, oldValue, newValue) -> poids = newValue);
        poidsField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                poidsField.increment(0); // won't change value, but will commit editor
                poids = poidsField.getValue();
            }
        });
    }

    public void handleAddTypeTissu(ActionEvent actionEvent) {
    }

    public void handleAddMatiere(ActionEvent actionEvent) {
    }

    public void handleAddTissage(ActionEvent actionEvent) {
    }

    public void handleGenerateReference(ActionEvent actionEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
