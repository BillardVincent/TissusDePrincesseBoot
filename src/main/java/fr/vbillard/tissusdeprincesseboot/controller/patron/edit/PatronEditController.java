package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.patron.edit.accordion.PatronVersionAccordionController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.PatronPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.controller.validators.NonNullValidator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.PatronVersionService;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils.TITLE_PANE_CUSTOM;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils.setStyle;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.textFieldToFirstCharOrX;
import static fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils.areValidatorsValid;

@Component
public class PatronEditController implements IController {

  @FXML
  private JFXTextField marqueField;
  @FXML
  private JFXTextField modeleField;
  @FXML
  private JFXTextField typeVetementField;
  @FXML
  private JFXButton addVersionBtn;
  @FXML
  private VBox tissuEtFournitureContainer;
  @FXML
  private JFXButton generateReferenceButton;
  @FXML
  private JFXTextField referenceField;
  @FXML
  public ImageView imagePane;
  @FXML
  public JFXComboBox<String> typeSupportCbBox;
  @FXML
  public JFXButton archiverBtn;
  @FXML
  private Accordion versionAccordion;

  private StageInitializer initializer;
  private final RootController root;
  private PatronDto patron;

  private final PatronPictureHelper pictureUtils;
  private final ModelMapper mapper;
  private boolean okClicked = false;
  private StageInitializer mainApp;

  private final PatronService patronService;
  private final PatronVersionService patronVersionService;

  private Validator[] validators;

  public PatronEditController(RootController root, PatronPictureHelper pictureUtils, ModelMapper mapper,
      PatronService patronService, PatronVersionService patronVersionService) {

    this.mapper = mapper;
    this.patronService = patronService;
    this.pictureUtils = pictureUtils;
    this.root = root;
    this.patronVersionService = patronVersionService;
  }

  @FXML
  private void initialize() {

    generateReferenceButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.MAGIC));
    generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));
    addVersionBtn.setGraphic(GlyphIconUtil.plusCircleTiny());

    validators = new Validator[] {new NonNullValidator<>(referenceField, "référence"),
        new NonNullValidator<>(marqueField, "marque"), new NonNullValidator<>(modeleField, "modèle"),
        new NonNullValidator<>(typeVetementField, "type")
    };

  }

  private void setDisabledButton() {
    boolean unregistredPatron = (patron == null || patron.getIdProperty() == null || patron.getId() == 0);

  }

  /**
   * Returns true if the user clicked OK, false otherwise.
   *
   * @return
   */
  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleSavePatron() {
    if (areValidatorsValid(initializer, validators)) {

      patron.setReference(referenceField.getText());
      patron.setMarque(marqueField.getText());
      patron.setModele(modeleField.getText());
      patron.setTypeVetement(typeVetementField.getText());
      patron.setTypeSupport(typeSupportCbBox.getValue());

      patron = patronService.create(patron);

      setDisabledButton();

      okClicked = true;
    }
  }

  @FXML
  private void handleCancel() {
    if (patron.getId() != 0) {
      root.displayPatronDetails(patron);
    } else {
      root.displayPatrons();
    }

  }

  @FXML
  private void handleGenerateReference() {
    StringBuilder sb = new StringBuilder();
    sb.append(textFieldToFirstCharOrX(marqueField))
        .append(textFieldToFirstCharOrX(modeleField))
        .append(textFieldToFirstCharOrX(typeVetementField))
        .append("-");
    boolean ref = true;
    int refNb = 0;
    while (ref) {
      refNb++;
      ref = patronService.existByReference(sb.toString() + refNb);
    }
    referenceField.setText(sb.append(refNb).toString());
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;

    if (data == null || data.getPatron() == null) {
      patron =
          mapper.map(new Patron("", "", "", "", "", false, SupportTypeEnum.NON_RENSEIGNE, null, null), PatronDto.class);
      setDisabledButton();

    } else {
      patron = data.getPatron();

      setDisabledButton();

      referenceField.setText(safePropertyToString(patron.getReferenceProperty()));
      marqueField.setText(safePropertyToString(patron.getMarqueProperty()));
      modeleField.setText(safePropertyToString(patron.getModeleProperty()));
      typeVetementField.setText(safePropertyToString(patron.getTypeVetementProperty()));
      FxUtils.buildComboBox(SupportTypeEnum.labels(), patron.getTypeSupportProperty(),
          SupportTypeEnum.NON_RENSEIGNE.label, typeSupportCbBox);
      pictureUtils.setPane(imagePane, patron);
      setBoutonArchiver();

      reload(-1);

    }
    displayRightPane();
  }

  private boolean allPanesClosed(Accordion accordion) {
   return Objects.isNull(accordion.getExpandedPane());
  }

  @FXML
  private void addPicture() {
    handleSavePatron();
    pictureUtils.addPictureLocal(patron);
  }

  @FXML
  private void addPictureWeb() {
    handleSavePatron();
    pictureUtils.addPictureWeb(patron);
  }

  @FXML
  private void addPictureFromClipboard() {
    handleSavePatron();
    pictureUtils.addPictureClipBoard(patron);

  }

  @FXML
  private void pictureExpend() {
    DevInProgressService.notImplemented();

  }

  @FXML
  public void archiver() {
    patron.setArchived(!patron.isArchived());
    patron = patronService.saveOrUpdate(patron);
    setBoutonArchiver();
  }

  private void setBoutonArchiver() {
    archiverBtn.setText(patron.isArchived() ? "Désarchiver" : "Archiver");
  }

  public void displayRightPane(TissuRequisDto tissuRequisDto, PatronVersionAccordionController parent) {
    tissuEtFournitureContainer.getChildren().clear();
    FxData data = new FxData();
    data.setParentController(parent);
    data.setTissuRequis(tissuRequisDto);
    tissuEtFournitureContainer.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT_TISSU_REQUIS, data));

  }

  public void displayRightPane(FournitureRequiseDto fournitureRequiseDto, PatronVersionAccordionController parent) {
    tissuEtFournitureContainer.getChildren().clear();
    FxData data = new FxData();
    data.setParentController(parent);
    data.setFournitureRequise(fournitureRequiseDto);
    tissuEtFournitureContainer.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT_FOURNITURE_REQUISE, data));

  }

  public void displayRightPane(PatronVersionDto patronVersionDto) {
    tissuEtFournitureContainer.getChildren().clear();
    FxData data = new FxData();
    data.setParentController(this);
    data.setPatronVersion(patronVersionDto);
    tissuEtFournitureContainer.getChildren().add(initializer.displayPane(PathEnum.PATRON_EDIT_PATRON_VERSION, data));
  }

  public void displayRightPane() {
    tissuEtFournitureContainer.getChildren().clear();
    FxData data = new FxData();
    data.setParentController(this);
    data.setPatron(patron);
    Pane p = initializer.displayPane(PathEnum.PATRON_EDIT_PATRON, data);
    tissuEtFournitureContainer.getChildren().add(p);

  }

  public void reload(int versionIdOpen) {
    versionAccordion.getPanes().clear();
    for (PatronVersion pv : patronVersionService.getByPatronId(patron.getId())){
      TitledPane pane = new TitledPane();
      pane.setText("Version : " + pv.getNom());
      setStyle(pane, TITLE_PANE_CUSTOM, true);
      FxData fxData = new FxData();
      fxData.setPatronVersion(patronVersionService.convert(pv));
      fxData.setParentController(this);
      Pane content = initializer.displayPane(PathEnum.PATRON_VERSION_ACCORDION, fxData);
      pane.setContent(content);
      pane.expandedProperty().addListener((obs, oldValue, newValue) -> {
        if (BooleanUtils.isTrue(newValue)) {
          displayRightPane(mapper.map(pv, PatronVersionDto.class));
        } else {
          if (allPanesClosed(versionAccordion)){
            displayRightPane();
          }
        }
      });

      versionAccordion.getPanes().add(pane);
      if (versionIdOpen == pv.getId()){
        versionAccordion.setExpandedPane(pane);
      }
    }
  }


  @FXML
  public void handleAjouterVersion() {
    PatronVersion pv = new PatronVersion();
    pv.setNom("nouvelle version");
    pv.setPatron(patronService.convert(patron));
    pv = patronVersionService.saveOrUpdate(pv);
    reload(pv.getId());
  }

}
