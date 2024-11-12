package fr.vbillard.tissusdeprincesseboot.controller.fourniture;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.color.ColorComponent;
import fr.vbillard.tissusdeprincesseboot.controller.components.FloatSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.FourniturePictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.controller.validators.NonNullValidator;
import fr.vbillard.tissusdeprincesseboot.controller.validators.Validator;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.utils.Constants;
import fr.vbillard.tissusdeprincesseboot.utils.DevInProgressService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.onChangeListener;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.safePropertyToString;
import static fr.vbillard.tissusdeprincesseboot.controller.validators.ValidatorUtils.areValidatorsValid;
import static fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils.startWithMajuscule;
import static javafx.collections.FXCollections.observableArrayList;

@Component
public class FournitureEditController implements IController {
  @FXML
  public JFXTextField descriptionField;
  @FXML
  public JFXTextField nomField;
  @FXML
  public JFXTextField lieuDachatField;
  @FXML
  public JFXComboBox<String> uniteField;
  @FXML
  public FloatSpinner quantiteField;
  @FXML
  public JFXComboBox<String> uniteSecField;
  @FXML
  public FloatSpinner quantiteSecField;
  @FXML
  public JFXComboBox<String> typeField;
  @FXML
  public JFXButton addTypeButton;
  @FXML
  public JFXTextField referenceField;
  @FXML
  public Label lieuDeStockageField;
  @FXML
  public JFXButton generateReferenceButton;
  @FXML
  public Label quantiteUtiliseeLabel;
  @FXML
  public ImageView imagePane;
  @FXML
  public JFXButton addPictureWebBtn;
  @FXML
  public JFXButton pictureExpendBtn;
  @FXML
  public JFXButton addPictureBtn;
  @FXML
  public JFXButton addPictureClipboardBtn;
  @FXML
  public Label imageNotSaved;
  @FXML
  public Label intitulePrimLbl;
  @FXML
  public Label intituleSecLbl;
  @FXML
  public JFXButton archiverBtn;
  @FXML
  public FontAwesomeIconView warningSaveIcon;
  @FXML
  public Label warningSaveLbl;
  @FXML
  public ColorComponent colorComp;
  @FXML
  public JFXButton changerLieuStockage;

  private final RootController root;
  private StageInitializer initializer;

  private FournitureDto fourniture;
  @Getter
  private boolean okClicked = false;

  private final TypeFournitureService typeService;
  private final FournitureService fournitureService;
  private final RangementService rangementService;
  private final FourniturePictureHelper pictureHelper;

  private final BooleanProperty hasChanged = new SimpleBooleanProperty(false);

  private Validator[] validators;

  public FournitureEditController(FourniturePictureHelper pictureHelper, FournitureService fournitureService,
      TypeFournitureService typeService, RootController root, RangementService rangementService) {
    this.fournitureService = fournitureService;
    this.typeService = typeService;
    this.pictureHelper = pictureHelper;
    this.root = root;
    this.rangementService = rangementService;
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;

    if (data == null || data.getFourniture() == null) {
      throw new IllegalData();
    }
    fourniture = data.getFourniture();
    if (fourniture == null || fourniture.getTypeNameProperty() == null) {
      fourniture = new FournitureDto();
    }

    typeField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(oldValue)) {
        TypeFourniture type = typeService.findTypeFourniture(newValue);

        uniteField.setDisable(false);
        quantiteField.setDisable(false);

        boolean dimSecondaireIsNull = type.getDimensionSecondaire() == null;

        quantiteField.setText("0.0");

        fourniture.setType(typeService.findTypeFourniture(newValue));
        uniteField.setItems(
                observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionPrincipale())));
        uniteField.setValue(type.getUnitePrincipaleConseillee() == null ? type.getDimensionPrincipale().getDefault().getLabel() : type.getUnitePrincipaleConseillee().getLabel());
        intitulePrimLbl.setText(type.getIntitulePrincipale());

        uniteSecField.setDisable(dimSecondaireIsNull);
        quantiteSecField.setDisable(dimSecondaireIsNull);
        quantiteSecField.setText("0.0");

        if (!dimSecondaireIsNull) {
          uniteSecField.setItems(
                  observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionSecondaire())));
          uniteSecField.setValue(type.getUniteSecondaireConseillee() == null ? type.getDimensionSecondaire().getDefault().getLabel() : type.getUniteSecondaireConseillee().getLabel());
          intituleSecLbl.setText(type.getIntituleSecondaire());
        } else {
          uniteSecField.setValue(Strings.EMPTY);
          intituleSecLbl.setText("N/A");
        }
      }
    });

   // TODO tester la solution :
    // Bindings.bindBidirectional(quantiteField.textProperty(), fourniture.getQuantiteProperty(), new NumberStringConverter());


    typeField.setItems(observableArrayList(typeService.getAllTypeFournituresValues()));
    typeField.setValue(safePropertyToString(fourniture.getTypeNameProperty()));

    quantiteField.setText(safePropertyToString(fourniture.getQuantiteProperty()));
    quantiteSecField.setText(safePropertyToString(fourniture.getQuantiteSecondaireProperty()));
    referenceField.setText(safePropertyToString(fourniture.getReferenceProperty()));
    descriptionField.setText(safePropertyToString(fourniture.getDescriptionProperty()));
    lieuDachatField.setText(safePropertyToString(fourniture.getLieuAchatProperty()));
    nomField.setText(safePropertyToString(fourniture.getNomProperty()));

    intitulePrimLbl.setText(startWithMajuscule(safePropertyToString(fourniture.getIntituleDimensionProperty())));
    intituleSecLbl.setText(startWithMajuscule(safePropertyToString(fourniture.getIntituleSecondaireProperty())));

    if (fourniture.getRangement() == null) {
      lieuDeStockageField.setText("Non renseigné");
    } else {
      lieuDeStockageField.setText(rangementService.getRangementPath(fourniture.getRangement().getId()));
    }

    if (fourniture.getType() != null) {
      uniteSecField.setValue(safePropertyToString(fourniture.getUniteSecondaireProperty()));
      uniteField.setItems(
          observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionPrincipale())));
      uniteSecField.setItems(
          observableArrayList(Unite.getValuesByDimension(fourniture.getType().getDimensionSecondaire())));
      uniteField.setValue(safePropertyToString(fourniture.getUniteProperty()));
      intitulePrimLbl.setText(fourniture.getType().getIntitulePrincipale());
      intituleSecLbl.setText(fourniture.getType().getIntituleSecondaire());

      Unite unite = Unite.getEnum(fourniture.getUnite());
      float facteur = unite == null ? 0f : unite.getFacteur();
      String abbreviation = unite == null ? Strings.EMPTY : unite.getAbbreviation();
      quantiteUtiliseeLabel.setText(fournitureService.getQuantiteUtilisee(fourniture.getId()) * facteur + " " + abbreviation);

    } else {
      uniteField.setDisable(true);
      quantiteField.setDisable(true);
      uniteSecField.setDisable(true);
      quantiteSecField.setDisable(true);
      quantiteUtiliseeLabel.setText(Strings.EMPTY);
    }

    warningSaveIcon.setVisible(false);
    warningSaveLbl.setVisible(false);

    pictureHelper.setPane(imagePane, fourniture);
    boolean tissuIsNew = fourniture.getId() == 0;
    addPictureWebBtn.setDisable(tissuIsNew);
    pictureExpendBtn.setDisable(tissuIsNew);
    addPictureBtn.setDisable(tissuIsNew);
    imageNotSaved.setVisible(tissuIsNew);
    addPictureClipboardBtn.setDisable(tissuIsNew);

    colorComp.initialize(initializer, fourniture.getColor(), pictureHelper.hasImage(fourniture) ? imagePane.getImage() : null);
    pictureHelper.setColorComponent(colorComp);
    GlyphIconUtil.generateIcon(warningSaveIcon, GlyphIconUtil.VERY_BIG_ICONE_SIZE, Constants.colorWarning);

    setBoutonArchiver();

    onChangeListener(new ObservableValue[]{ referenceField.textProperty(), quantiteField.textProperty(),
            quantiteSecField.textProperty(), intitulePrimLbl.textProperty(), descriptionField.textProperty(),
            typeField.valueProperty(), uniteField.valueProperty(), uniteSecField.valueProperty(),
            intituleSecLbl.textProperty(),lieuDachatField.textProperty(), nomField.textProperty(),
            colorComp.getColorProperty()
    }, hasChanged);

    hasChanged.addListener((observable, oldValue, newValue) -> {
      warningSaveIcon.setVisible(newValue);
      warningSaveLbl.setVisible(newValue);
    });
  }

  @FXML
  public void initialize() {
    addTypeButton.setGraphic(GlyphIconUtil.plusCircleTiny());
    FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
    generateReferenceButton.setGraphic(magicIcon);
    generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

    validators =
        new Validator[] {new NonNullValidator<>(referenceField, "référence"), new NonNullValidator<>(typeField, "type"),
            new NonNullValidator<>(uniteField, "unité primaire")};
  }

  @FXML
  public void handleOk() {
    if (areValidatorsValid(initializer, validators)) {

      setFournitureFromFields();
      okClicked = true;
      hasChanged.setValue(false);
    }
  }

  private void setFournitureFromFields() {
    fourniture.setReference(referenceField.getText());
    fourniture.setQuantite(Float.parseFloat(quantiteField.getText()));
    fourniture.setQuantiteSec(Float.parseFloat(quantiteSecField.getText()));
    fourniture.setIntituleDimension(intitulePrimLbl.getText());
    fourniture.setDescription(descriptionField.getText());
    fourniture.setType(typeService.findTypeFourniture(typeField.getValue()));
    fourniture.setUnite(Unite.getEnum(uniteField.getValue()));
    fourniture.setUniteSecondaire(Unite.getEnum(uniteSecField.getValue()));
    fourniture.setIntituleSecondaire(intituleSecLbl.getText());
    fourniture.setLieuAchat(lieuDachatField.getText());
    fourniture.setNom(nomField.getText());
    fourniture.setColor(colorComp.getColor());

    fourniture = fournitureService.saveOrUpdate(fourniture);
  }

  @FXML
  public void handleCancel() {
    if (fourniture.getId() != 0) {
      root.displayFournituresDetails(fourniture);
    } else {
      root.displayFourniture();
    }
  }

  @FXML
  public void handleAddType() {
    initializer.displayModale(PathEnum.TYPE_FOURNITURE, null, "Fourniture");

    typeField.setItems(observableArrayList(typeService.getAllTypeFournituresValues()));
    typeField.setValue(safePropertyToString(fourniture.getTypeNameProperty()));

  }

  @FXML
  public void handleGenerateReference() {
    StringBuilder sb = new StringBuilder();
    sb.append(typeField == null || Strings.isBlank(typeField.getValue()) ? "XXX" : getSubstringValueMax3(typeField));

    boolean refExists = true;
    int refNb = 0;
    while (refExists) {
      refNb++;
      refExists = fournitureService.existByReference(sb.toString() + refNb);
    }
    referenceField.setText(sb.append(refNb).toString());
  }

  private String getSubstringValueMax3(JFXComboBox<String> field){
    return field.getValue().length() > 3 ? typeField.getValue().substring(0, 3) : typeField.getValue();
  }

  @FXML
  public void addPicture() {
    if (areValidatorsValid(initializer, validators)) {
      setFournitureFromFields();
      pictureHelper.addPictureLocal(fourniture);
    }
  }

  @FXML
  public void addPictureWeb() {
    if (areValidatorsValid(initializer, validators)) {
      setFournitureFromFields();
      pictureHelper.addPictureWeb(fourniture);
    }
  }

  @FXML
  public void addPictureFromClipboard() {
    if (areValidatorsValid(initializer, validators)) {
      setFournitureFromFields();
      pictureHelper.addPictureClipBoard(fourniture);
    }
  }

  @FXML
  public void pictureExpend() {
    DevInProgressService.notImplemented();
  }

  @FXML
  public void archiver() {
    fourniture.setArchived(!fourniture.isArchived());
    fourniture = fournitureService.saveOrUpdate(fourniture);
    setBoutonArchiver();
  }

  private void setBoutonArchiver() {
    archiverBtn.setText(fourniture.isArchived() ? "Désarchiver" : "Archiver");
  }

  public void handleStockage() {
    if (!hasChanged.get() || ButtonType.OK.equals(
            ShowAlert.warn(initializer.getPrimaryStage(), "Données non sauvegardées",
                            "Des modifications risquent d'être perdues",
                            "Vous allez être redirigé(e) vers la section \"Rangement\". Des modifications " +
                                    "ont été faites, mais n'ont pas été enregistrées. Souhaitez vous tout de même" +
                                    " continuer?")
                    .orElse(ButtonType.CANCEL))) {
      FxData data = new FxData();
      data.setFourniture(fourniture);
      root.displayFournitureSelected(data);
    }
  }
}
