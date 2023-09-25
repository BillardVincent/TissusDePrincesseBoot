package fr.vbillard.tissusdeprincesseboot.controller.patron;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.IntegerSpinner;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.PatronSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.*;
import static java.lang.Math.round;

@Component
public class PatronSearchController implements IController {

  private static final String AUCUN_FILTRE = "Aucun filtre";
  public static final String CHOIX = "Choix";

  @FXML
  public JFXTextField referenceField;
  @FXML
  public JFXTextField marqueField;
  @FXML
  public JFXTextField modeleField;
  @FXML
  public JFXTextField typeVetementField;
  @FXML
  public JFXTextField descriptionField;
  @FXML
  public JFXButton typeButton;
  @FXML
  public Label typeLbl;
  @FXML
  public JFXButton tissageButton;
  @FXML
  public Label tissageLbl;
  @FXML
  public Label matiereLbl;
  @FXML
  public JFXCheckBox lourdCBox;
  @FXML
  public JFXCheckBox moyenCBox;
  @FXML
  public JFXCheckBox legerCBox;
  @FXML
  public JFXCheckBox ncCBox;
  @FXML
  public JFXCheckBox supportPapierCBox;
  @FXML
  public JFXCheckBox supportPdfCBox;
  @FXML
  public JFXCheckBox supportProjectionCBox;
  @FXML
  public JFXCheckBox supportNcCBox;
  @FXML
  public IntegerSpinner longueurFieldMax;
  @FXML
  public IntegerSpinner laizeFieldMax;
  @FXML
  public JFXRadioButton archive;
  @FXML
  public JFXRadioButton notArchive;
  @FXML
  public JFXRadioButton indifferentArchive;

  private final ToggleGroup archiveGroup = new ToggleGroup();

  private final RootController root;
  private StageInitializer initializer;

  private final MatiereService matiereService;
  private final TissageService tissageService;
  private final UserPrefService userPrefService;

  private List<String> tissageValuesSelected = new ArrayList<>();
  private List<String> typeValuesSelected = new ArrayList<>();
  private List<String> matiereValuesSelected = new ArrayList<>();

  private int margeHauteLeger;
  private int margeBasseMoyen;
  private int margeHauteMoyen;
  private int margeBasseLourd;

  private boolean okClicked = false;

  public PatronSearchController(RootController root, MatiereService matiereService, TissageService tissageService,
      UserPrefService userPrefService) {
    this.root = root;
    this.matiereService = matiereService;
    this.tissageService = tissageService;
    this.userPrefService = userPrefService;
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;

    setData(data);

    UserPref pref = userPrefService.getUser();

    margeHauteLeger = round(pref.getMinPoidsMoyen() + pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
    margeBasseMoyen = round(pref.getMinPoidsMoyen() - pref.getMinPoidsMoyen() * pref.getPoidsMargePercent());
    margeHauteMoyen = round(pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());
    margeBasseLourd = round(pref.getMaxPoidsMoyen() - pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent());


    setToggleColor(lourdCBox, moyenCBox, legerCBox, ncCBox, supportPapierCBox, supportPdfCBox,
        supportProjectionCBox, supportNcCBox, notArchive, indifferentArchive, archive);

    setToggleGroup(archiveGroup, notArchive, indifferentArchive, archive);
  }

  private void setData(FxData data) {
    if (data != null && data.getSpecification() instanceof PatronSpecification) {
      PatronSpecification spec = (PatronSpecification) data.getSpecification();
      if (spec.getDescription() != null && Strings.isNotBlank(spec.getDescription().getContainsMultiple())) {
        descriptionField.setText(spec.getDescription().getContainsMultiple());
      }
      setTextFieldFromCharacterSearch(typeVetementField, spec.getTypeVetement());
      setTextFieldFromCharacterSearch(marqueField, spec.getMarque());
      setTextFieldFromCharacterSearch(modeleField, spec.getModele());

      List<String> types = null;
      if (spec.getTypeTissu() != null) {
        types = spec.getTypeTissu().stream().map(TypeTissuEnum::getLabel).collect(Collectors.toList());
      }
      setSelection(types, typeValuesSelected, typeLbl);

      List<String> matieres = null;
      if (spec.getMatieres() != null) {
        matieres = spec.getMatieres().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
      }
      setSelection(matieres, matiereValuesSelected, matiereLbl);

      List<String> tissages = null;
      if (spec.getTissages() != null) {
        tissages = spec.getTissages().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
      }
      setSelection(tissages, tissageValuesSelected, tissageLbl);

      setTextFieldMaxFromNumericSearch(longueurFieldMax, spec.getLongueur());
      setTextFieldMaxFromNumericSearch(laizeFieldMax, spec.getLaize());

      setToggleGroupFromBoolean(spec.getArchived(), archive, notArchive, indifferentArchive);

      lourdCBox.setSelected(CollectionUtils.isEmpty(spec.getPoids()) || spec.getPoids().contains(GammePoids.LOURD));
      moyenCBox.setSelected(CollectionUtils.isEmpty(spec.getPoids()) || spec.getPoids().contains(GammePoids.MOYEN));
      legerCBox.setSelected(CollectionUtils.isEmpty(spec.getPoids()) || spec.getPoids().contains(GammePoids.LEGER));
      ncCBox.setSelected(
          CollectionUtils.isEmpty(spec.getPoids()) || spec.getPoids().contains(GammePoids.NON_RENSEIGNE));

      supportPapierCBox.setSelected(
          CollectionUtils.isEmpty(spec.getSupport()) || spec.getSupport().contains(SupportTypeEnum.PAPIER));
      supportPdfCBox.setSelected(
          CollectionUtils.isEmpty(spec.getSupport()) || spec.getSupport().contains(SupportTypeEnum.PDF));
      supportProjectionCBox.setSelected(
          CollectionUtils.isEmpty(spec.getSupport()) || spec.getSupport().contains(SupportTypeEnum.PROJECTION));
      supportNcCBox.setSelected(
          CollectionUtils.isEmpty(spec.getSupport()) || spec.getSupport().contains(SupportTypeEnum.NON_RENSEIGNE));


    } else {

      typeLbl.setText(AUCUN_FILTRE);
      typeValuesSelected = new ArrayList<>();
      matiereLbl.setText(AUCUN_FILTRE);
      matiereValuesSelected = new ArrayList<>();
      tissageLbl.setText(AUCUN_FILTRE);
      tissageValuesSelected = new ArrayList<>();

      longueurFieldMax.setText(Strings.EMPTY);
      laizeFieldMax.setText(Strings.EMPTY);
      descriptionField.setText(Strings.EMPTY);
      typeVetementField.setText(Strings.EMPTY);
      marqueField.setText(Strings.EMPTY);
      modeleField.setText(Strings.EMPTY);

      lourdCBox.setSelected(true);
      moyenCBox.setSelected(true);
      legerCBox.setSelected(true);
      ncCBox.setSelected(true);

      supportPapierCBox.setSelected(true);
      supportPdfCBox.setSelected(true);
      supportProjectionCBox.setSelected(true);
      supportNcCBox.setSelected(true);

      notArchive.setSelected(true);
    }
  }

  @FXML
  private void initialize(){
    // nothing to initialize
  }

  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleOk() {

    List<Matiere> matieres = matiereService.findMatiere(matiereValuesSelected);

    List<Tissage> tissages = tissageService.tissageValuesSelected(tissageValuesSelected);

    List<TypeTissuEnum> types = null;
    if (typeValuesSelected != null && !typeValuesSelected.isEmpty()) {
      types = new ArrayList<>();
      for (String s : typeValuesSelected) {
        types.add(TypeTissuEnum.getEnum(s));
      }
    }

    CharacterSearch description = textFieldToCharacterSearchMultiple(descriptionField);

    CharacterSearch reference = textFieldToCharacterSearch(referenceField);

    CharacterSearch marque = textFieldToCharacterSearch(marqueField);

    CharacterSearch modele = textFieldToCharacterSearch(modeleField);

    CharacterSearch typeVetement = textFieldToCharacterSearch(typeVetementField);

    NumericSearch<Integer> longueur = textFieldToMaxNumericSearch(longueurFieldMax);

    List<SupportTypeEnum> support = new ArrayList<>();
    if (supportPapierCBox.isSelected()) {
      support.add(SupportTypeEnum.PAPIER);
    }
    if (supportPdfCBox.isSelected()) {
      support.add(SupportTypeEnum.PDF);
    }
    if (supportProjectionCBox.isSelected()) {
      support.add(SupportTypeEnum.PROJECTION);
    }
    if (supportNcCBox.isSelected()) {
      support.add(SupportTypeEnum.NON_RENSEIGNE);
    }
    if (support.size() == 4) {
      support = null;
    }

    Boolean archived = getBooleanFromRadioButtons(archive, notArchive, indifferentArchive);

    PatronSpecification specification =
        PatronSpecification.builder().description(description).reference(reference).matieres(matieres).typeTissu(types)
            .tissages(tissages).marque(marque).modele(modele).typeVetement(typeVetement).longueur(longueur)
            .support(support).archived(archived).build();

    root.displayPatrons(specification);
  }

  @FXML
  private void handleCancel() {
    FxData data = new FxData();
    data.setSpecification(new PatronSpecification());
    setData(data);
  }

  @FXML
  private void choiceType() {
    setSelectionFromChoiceBoxModale(TypeTissuEnum.labels(), typeValuesSelected, typeLbl, true);
  }

  @FXML
  private void choiceMatiere() {
    setSelectionFromChoiceBoxModale(matiereService.getAllValues(), matiereValuesSelected, matiereLbl, true);
  }

  @FXML
  private void choiceTissage() {
    setSelectionFromChoiceBoxModale(tissageService.getAllValues(), tissageValuesSelected, tissageLbl, true);
  }

  @FXML
  private void poidsHelp() {
    ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
        "Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à " + DECIMAL_FORMAT.format(
            margeHauteLeger) + ", Moyen = entre " + DECIMAL_FORMAT.format(margeBasseMoyen) + " et " + DECIMAL_FORMAT.format(margeHauteMoyen)
            + ", Lourd = supérieur à " + DECIMAL_FORMAT.format(margeBasseLourd));
  }

}
