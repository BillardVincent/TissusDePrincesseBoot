package fr.vbillard.tissusdeprincesseboot.controller.tissu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.color.ColorComponent;
import fr.vbillard.tissusdeprincesseboot.controller.components.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.controller.validators.NonNullValidator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.ConstantesMetier;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.*;
import static fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils.areValidatorsValid;

@Component
public class TissuEditController implements IController {
    @FXML
    public JFXToggleButton decatiField;
    @FXML
    public JFXTextField descriptionField;
    @FXML
    public JFXTextField lieuDachatField;
    @FXML
    public IntegerSpinner poidsField;
    @FXML
    public JFXComboBox<String> unitePoidsField;
    @FXML
    public IntegerSpinner laizeField;
    @FXML
    public IntegerSpinner longueurField;
    @FXML
    public JFXToggleButton chuteField;
    @FXML
    public JFXComboBox<String> typeField;
    @FXML
    public JFXComboBox<String> matiereField;
    @FXML
    public JFXButton addMatiereButton;
    @FXML
    public JFXComboBox<String> tissageField;
    @FXML
    public JFXButton addTissageButton;
    @FXML
    public JFXTextField referenceField;
    @FXML
    public JFXButton generateReferenceButton;
    @FXML
    public Label ancienneValeurLabel;
    @FXML
    public Label ancienneValeurInfo;
    @FXML
    public Label consommeLabel;
    @FXML
    public Label consommeInfo;
    @FXML
    public ImageView imagePane;
    @FXML
    public JFXButton addPictureWebBtn;
    @FXML
    public JFXButton pictureExpendBtn;
    @FXML
    public JFXButton addPictureBtn;
    @FXML
    public Label imageNotSaved;
    @FXML
    public JFXButton addPictureClipboardBtn;
    @FXML
    public JFXButton archiverBtn;
    @FXML
    public FontAwesomeIconView warningSaveIcon;
    @FXML
    public Label warningSaveLbl;
    @FXML
    public ColorComponent colorComp;
    @FXML
    public Label lieuDeStockageField;
    @FXML
    public JFXButton changerLieuStockage;

    private final RootController root;
    private StageInitializer initializer;

    private TissuDto tissu;
    private boolean okClicked = false;

    private final MapperService mapper;
    private final MatiereService matiereService;
    private final TissageService tissageService;
    private final TissuService tissuService;
    private final RangementService rangementService;
    private final TissuPictureHelper pictureHelper;
    private final ConstantesMetier constantesMetier;

    private BooleanProperty hasChanged = new SimpleBooleanProperty(false);

    private Validator[] validators;

    public TissuEditController(TissuPictureHelper pictureHelper, MapperService mapper, TissuService tissuService,
            MatiereService matiereService, TissageService tissageService, RootController root,
            RangementService rangementService, ConstantesMetier constantesMetier) {
        this.mapper = mapper;
        this.tissuService = tissuService;
        this.matiereService = matiereService;
        this.tissageService = tissageService;
        this.pictureHelper = pictureHelper;
        this.root = root;
        this.rangementService = rangementService;
        this.constantesMetier = constantesMetier;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;

        if (data == null || data.getTissu() == null) {
            throw new IllegalData();
        }
        tissu = data.getTissu();
        if (tissu == null || tissu.getChuteProperty() == null) {
            tissu = mapper.map(new Tissu(0, "", 0, 0, "", null, TypeTissuEnum.NON_RENSEIGNE, 0,
                    UnitePoids.NON_RENSEIGNE, false, "", null, false, false));
        }

        longueurField.setText(safePropertyToString(tissu.getLongueurProperty()));
        laizeField.setText(safePropertyToString(tissu.getLaizeProperty()));
        poidsField.setText(safePropertyToString(tissu.getPoidseProperty()));
        referenceField.setText(safePropertyToString(tissu.getReferenceProperty()));
        descriptionField.setText(safePropertyToString(tissu.getDescriptionProperty()));
        decatiField.setSelected(tissu.getDecatiProperty() != null && tissu.isDecati());
        lieuDachatField.setText(safePropertyToString(tissu.getLieuAchatProperty()));
        chuteField.setSelected(tissu.getChuteProperty() != null && tissu.isChute());
        ancienneValeurInfo.setText(tissu.getLongueurProperty() == null ? "0" : Integer.toString(tissu.getLongueur()));
        consommeInfo.setText(Integer.toString(tissuService.getLongueurUtilisee(tissu.getId())));

        unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
        unitePoidsField.setValue(
                tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label : tissu.getUnitePoids());

        typeField.setItems(FXCollections.observableArrayList(TypeTissuEnum.labels()));
        typeField.setValue(
                tissu.getUnitePoidsProperty() == null ? TypeTissuEnum.NON_RENSEIGNE.label : tissu.getTypeTissu());

        matiereField.setItems(FXCollections.observableArrayList(matiereService.getAllMatieresValues()));
        matiereField.setValue(safePropertyToString(tissu.getMatiereProperty()));

        tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
        tissageField.setValue(safePropertyToString(tissu.getTissageProperty()));

        if (tissu.getRangement() == null) {
            lieuDeStockageField.setText("Non renseigné");
        } else {
            lieuDeStockageField.setText(rangementService.getRangementPath(tissu.getRangement().getId()));
        }

        pictureHelper.setPane(imagePane, tissu);
        colorComp.initialize(initializer, tissu.getColor(), imagePane.getImage());

        boolean tissuIsNew = tissu.getId() == 0;
        addPictureWebBtn.setDisable(tissuIsNew);
        pictureExpendBtn.setDisable(tissuIsNew);
        addPictureBtn.setDisable(tissuIsNew);
        imageNotSaved.setVisible(tissuIsNew);
        addPictureClipboardBtn.setDisable(tissuIsNew);
        archiverBtn.setDisable(tissuIsNew);
        warningSaveIcon.setVisible(false);
        warningSaveLbl.setVisible(false);
        GlyphIconUtil.generateIcon(warningSaveIcon, GlyphIconUtil.VERY_BIG_ICONE_SIZE, Constants.colorWarning);
        setBoutonArchiver();

        onChangeListener(new ObservableValue[] { longueurField.textProperty(), laizeField.textProperty(),
                poidsField.textProperty(), referenceField.textProperty(), descriptionField.textProperty(),
                decatiField.selectedProperty(), lieuDachatField.textProperty(), chuteField.selectedProperty(),
                unitePoidsField.valueProperty(), typeField.valueProperty(), matiereField.valueProperty(),
                tissageField.valueProperty(), colorComp.getColorProperty()
        }, hasChanged);

        hasChanged.addListener((observable, oldValue, newValue) -> {
            warningSaveIcon.setVisible(newValue);
            warningSaveLbl.setVisible(newValue);
        });

    }

    @FXML
    private void initialize() {
        addTissageButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        addMatiereButton.setGraphic(GlyphIconUtil.plusCircleTiny());
        FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
        generateReferenceButton.setGraphic(magicIcon);
        generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

        validators = new Validator[] { new NonNullValidator<>(referenceField, "référence"),
                new NonNullValidator<>(matiereField, "matière"),
                new NonNullValidator<>(unitePoidsField, "unité de poids"),
                new NonNullValidator<>(poidsField, "poids"),
                new NonNullValidator<>(typeField, "type") };
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (areValidatorsValid(initializer, validators)) {
            hasChanged.setValue(false);
            setTissuFromFields();
            okClicked = true;
            //root.displayTissusDetails(tissu);
        }
    }

    private void setTissuFromFields() {
        tissu.setReference(referenceField.getText());
        tissu.setLongueur(Integer.parseInt(longueurField.getText()));
        tissu.setLaize(Integer.parseInt(laizeField.getText()));
        tissu.setDescription(descriptionField.getText());
        tissu.setMatiere(matiereField.getValue());
        tissu.setTypeTissu(typeField.getValue());
        tissu.setPoids(Integer.parseInt(poidsField.getText()));
        tissu.setUnitePoids(unitePoidsField.getValue());
        tissu.setDecati(decatiField.isSelected());
        tissu.setLieuAchat(lieuDachatField.getText());
        tissu.setChute(chuteField.isSelected());
        tissu.setTissage(tissageField.getValue());
        tissu.setColor(colorComp.getColor());

        tissu = tissuService.saveOrUpdate(tissu);
    }

    @FXML
    private void handleCancel() {
        hasChanged.setValue(false);
        if (tissu.getId() != 0) {
            root.displayTissusDetails(tissu);
        } else {
            root.displayTissus();
        }
    }

    @FXML
    private void handleAddMatiere() {
        initializer.displayModale(PathEnum.MATIERE, null, "Matière");

        matiereField.setItems(FXCollections.observableArrayList(matiereService.getAllMatieresValues()));
        matiereField.setValue(safePropertyToString(tissu.getMatiereProperty()));
    }

    @FXML
    private void handleAddTissage() {
        initializer.displayModale(PathEnum.TISSAGE, null, "Tissage");

        tissageField.setItems(FXCollections.observableArrayList(tissageService.getAllValues()));
        tissageField.setValue(safePropertyToString(tissu.getTissageProperty()));
    }

    @FXML
    private void handleGenerateReference() {
        StringBuilder sb = new StringBuilder();
        sb.append(textFieldToFirstCharOrX(typeField)).append(textFieldToFirstCharOrX(matiereField))
                .append(textFieldToFirstCharOrX(tissageField)).append("-");
        if (chuteField.isSelected()) {
            sb.append("cp-");
        }
        boolean ref = true;
        int refNb = 0;
        while (ref) {
            refNb++;
            ref = tissuService.existByReference(sb.toString() + refNb);
        }
        referenceField.setText(sb.append(refNb).toString());
    }

    @FXML
    private void addPicture() {
        if (areValidatorsValid(initializer, validators)) {
            setTissuFromFields();

            pictureHelper.addPictureLocal(tissu);
        }
    }

    @FXML
    private void addPictureWeb() {
        if (areValidatorsValid(initializer, validators)) {
            setTissuFromFields();
            pictureHelper.addPictureWeb(tissu);
        }
    }

    @FXML
    private void addPictureFromClipboard() {
        if (areValidatorsValid(initializer, validators)) {
            setTissuFromFields();
            pictureHelper.addPictureClipBoard(tissu);
        }
    }

    @FXML
    private void pictureExpend() {
        DevInProgressService.notImplemented();
    }

    @FXML
    public void poidsHelp() {
        ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids",
                "Un poids renseigné en g/m sera converti en g/m² lors de l'enregistrement (laize doit être renseigné). Un "
                        + "tissu léger sera entre  0 et " + constantesMetier.getMinPoidsMoyen()
                        + "g/m², un tissu moyen entre " + constantesMetier.getMinPoidsMoyen() + "g/m² et "
                        + constantesMetier.getMaxPoidsMoyen() + "g/m², un tissu lourd aura un poids supérieur à "
                        + constantesMetier.getMaxPoidsMoyen() + "g/m².");
    }

    @FXML
    public void archiver() {
        tissu.setArchived(!tissu.isArchived());
        tissu = tissuService.saveOrUpdate(tissu);
        setBoutonArchiver();
    }

    private void setBoutonArchiver() {
        archiverBtn.setText(tissu.isArchived() ? "Désarchiver" : "Archiver");
    }

    public void handleStockage() {
        if (!hasChanged.get() || ButtonType.OK.equals(
                ShowAlert.warn(initializer.getPrimaryStage(), "Données non sauvegardées",
                                "Des modifications risquent d'être perdues",
                                "Vous allez être redirigé(e) vers la section \"Rangement\". Des modifications ont été faites, mais n'ont pas été enregistrées. Souhaitez vous tout de même continuer?")
                        .orElse(ButtonType.CANCEL))) {
            FxData data = new FxData();
            data.setTissu(tissu);
            root.displayTissuSelected(data);
        }
    }
}
