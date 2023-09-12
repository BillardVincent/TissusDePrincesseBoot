package fr.vbillard.tissusdeprincesseboot.controller.tissu;

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
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.CharacterSearch;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.*;

@Component
public class TissuSearchController implements IController {

  private static final String AUCUN_FILTRE = "Aucun filtre";
  private TissuSpecification specification;

  @FXML
  public JFXTextField referenceField;
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
  public JFXTextField descriptionField;
  @FXML
  public JFXTextField lieuDachatField;
  @FXML
  public JFXCheckBox lourdCBox;
  @FXML
  public JFXCheckBox moyenCBox;
  @FXML
  public JFXCheckBox legerCBox;
  @FXML
  public JFXCheckBox ncCBox;
  @FXML
  public IntegerSpinner laizeFieldMin;
  @FXML
  public IntegerSpinner laizeFieldMax;
  @FXML
  public JFXCheckBox longueurUtilisableCBox;
  @FXML
  public IntegerSpinner longueurFieldMin;
  @FXML
  public IntegerSpinner longueurFieldMax;
  @FXML
  public JFXRadioButton decatiTrue;
  @FXML
  public JFXRadioButton decatiFalse;
  @FXML
  public JFXRadioButton decatiAll;
  @FXML
  public JFXRadioButton chute;
  @FXML
  public JFXRadioButton coupon;
  @FXML
  public JFXRadioButton chuteEtCoupon;
  @FXML
  public JFXRadioButton archive;
  @FXML
  public JFXRadioButton notArchive;
  @FXML
  public JFXRadioButton indifferentArchive;

  private StageInitializer initializer;

  private boolean okClicked = false;

  private final ToggleGroup decatiGroup = new ToggleGroup();
  private final ToggleGroup chuteGroup = new ToggleGroup();
  private final ToggleGroup archiveGroup = new ToggleGroup();

  private List<String> tissageValuesSelected = new ArrayList<>();
  private List<String> typeValuesSelected = new ArrayList<>();
  private List<String> matiereValuesSelected = new ArrayList<>();

  private final int margeHauteLeger;
  private final int margeBasseMoyen;
  private final int margeHauteMoyen;
  private final int margeBasseLourd;
  private static final DecimalFormat df = new DecimalFormat("#.##");

  private final RootController root;
  private final MatiereService matiereService;
  private final TissageService tissageService;
  private final UserPrefService prefService;

  public TissuSearchController(MatiereService matiereService, TissageService tissageService, RootController root,
      UserPrefService prefService) {
    this.matiereService = matiereService;
    this.tissageService = tissageService;
    this.root = root;
    this.prefService = prefService;

    UserPref pref = prefService.getUser();

    margeHauteLeger = pref.margeHauteLeger();
    margeBasseMoyen = pref.margeBasseMoyen();
    margeHauteMoyen = pref.margeHauteMoyen();
    margeBasseLourd = pref.margeBasseLourd();
  }

  @Override
  public void setStageInitializer(StageInitializer initializer, FxData data) {
    this.initializer = initializer;

    setToggleGroup(decatiGroup, decatiAll, decatiFalse, decatiTrue);
    setToggleGroup(chuteGroup, chuteEtCoupon, coupon, chute);
    setToggleGroup(archiveGroup, notArchive, indifferentArchive, archive);
    setToggleColor(lourdCBox, moyenCBox, legerCBox, decatiAll, decatiFalse, decatiTrue, chuteEtCoupon, coupon,
        chute, notArchive, indifferentArchive, archive, longueurUtilisableCBox);

    typeLbl.setText(AUCUN_FILTRE);
    matiereLbl.setText(AUCUN_FILTRE);
    tissageLbl.setText(AUCUN_FILTRE);

    lourdCBox.setSelected(true);
    moyenCBox.setSelected(true);
    legerCBox.setSelected(true);

    // TODO mettre en place NC
    ncCBox.setSelected(false);
    ncCBox.setVisible(false);

    setData(data);

  }

  private void setData(FxData data) {
    if (data != null && data.getSpecification() != null && data.getSpecification() instanceof TissuSpecification) {

      specification = (TissuSpecification) data.getSpecification();

      if (specification.getDescription() != null && Strings.isNotBlank(
          specification.getDescription().getContainsMultiple())) {
        descriptionField.setText(specification.getDescription().getContainsMultiple());
      }

      setTextFieldFromCharacterSearch(referenceField, specification.getReference());

      setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
      setTextFieldMinFromNumericSearch(longueurFieldMin, specification.getLongueur());
      setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());
      setTextFieldMinFromNumericSearch(laizeFieldMin, specification.getLaize());

      List<String> types = null;
      if (specification.getTypeTissu() != null) {
        types = specification.getTypeTissu().stream().map(TypeTissuEnum::getLabel).collect(Collectors.toList());
      }
      setSelection(types, typeValuesSelected, typeLbl);

      List<String> matieres = null;
      if (specification.getMatieres() != null) {
        matieres =
            specification.getMatieres().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
      }
      setSelection(matieres, matiereValuesSelected, matiereLbl);

      List<String> tissages = null;
      if (specification.getTissages() != null) {
        tissages =
            specification.getTissages().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList());
      }
      setSelection(tissages, tissageValuesSelected, tissageLbl);

      setTextFieldMaxFromNumericSearch(longueurFieldMax, specification.getLongueur());
      setTextFieldMaxFromNumericSearch(laizeFieldMax, specification.getLaize());

      setPoidsFromSpec();

      setToggleGroupFromBoolean(specification.getArchived(), archive, notArchive, indifferentArchive);
      setToggleGroupFromBoolean(specification.getDecati(), decatiTrue, decatiFalse, decatiAll);
      setToggleGroupFromBoolean(specification.getChute(), chute, coupon, chuteEtCoupon);

    } else {
      specification = new TissuSpecification();

      typeLbl.setText(AUCUN_FILTRE);
      typeValuesSelected = new ArrayList<>();
      matiereLbl.setText(AUCUN_FILTRE);
      matiereValuesSelected = new ArrayList<>();
      tissageLbl.setText(AUCUN_FILTRE);
      tissageValuesSelected = new ArrayList<>();

      longueurFieldMax.setText(Strings.EMPTY);
      laizeFieldMax.setText(Strings.EMPTY);
      descriptionField.setText(Strings.EMPTY);

      lourdCBox.setSelected(true);
      moyenCBox.setSelected(true);
      legerCBox.setSelected(true);
      ncCBox.setSelected(true);
    }
  }

  private void setPoidsFromSpec() {
    if (specification.getPoidsNC() != null) {
      ncCBox.setSelected(specification.getPoidsNC());
    } else {
      ncCBox.setSelected(specification.getPoids() == null);
    }

    if (specification.getPoids() != null) {

      Integer min = specification.getPoids().getGreaterThanEqual();
      Integer max = specification.getPoids().getLessThanEqual();

      boolean minIsNullOrZero = min == null || min == 0;

      if (minIsNullOrZero && (max == null || max == 0)) {
        lourdCBox.setSelected(true);
        moyenCBox.setSelected(true);
        legerCBox.setSelected(true);
      } else {
        legerCBox.setSelected(minIsNullOrZero);
        moyenCBox.setSelected(min != null && min >= margeBasseMoyen && max != null && max > margeHauteLeger);
        lourdCBox.setSelected(max != null && max > margeHauteMoyen);
      }
    } else {
      lourdCBox.setSelected(true);
      moyenCBox.setSelected(true);
      legerCBox.setSelected(true);
    }
  }

  @FXML
  private void initialize() {
    // nothing to do here
  }

  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleCancel() {
    FxData data = new FxData();
    data.setSpecification(new TissuSpecification());
    setData(data);
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

    NumericSearch<Integer> longueurSearch = setNumericSearch(longueurFieldMin, longueurFieldMax);

    NumericSearch<Integer> laizeSearch = setNumericSearch(laizeFieldMin, laizeFieldMax);

    CharacterSearch description = textFieldToCharacterSearchMultiple(descriptionField);

    CharacterSearch reference = textFieldToCharacterSearch(referenceField);

    NumericSearch<Integer> poidsSearch = numericSearch(lourdCBox, moyenCBox, legerCBox, prefService.getUser());

    Boolean poidsSearchNc = null; // ncCBox.isSelected();

    Boolean decati = getBooleanFromRadioButtons(decatiTrue, decatiFalse, decatiAll);
    Boolean chuteOuCoupon = getBooleanFromRadioButtons(chute, coupon, chuteEtCoupon);
    Boolean archived = getBooleanFromRadioButtons(archive, notArchive, indifferentArchive);

    specification =
        TissuSpecification.builder().reference(reference).description(description).chute(chuteOuCoupon).decati(decati)
            .laize(laizeSearch).poids(poidsSearch).poidsNC(poidsSearchNc).longueur(longueurSearch).typeTissu(types)
            .matieres(matieres).tissages(tissages).archived(archived).build();

    root.displayTissu(specification);
  }

  @FXML
  private void lourdAction() {
    if (!moyenCBox.isSelected() && legerCBox.isSelected()) {
      moyenCBox.setSelected(true);
    }
  }

  @FXML
  private void moyenAction() {
    if (!moyenCBox.isSelected() && lourdCBox.isSelected() && legerCBox.isSelected()) {
      lourdCBox.setSelected(false);
    }
  }

  @FXML
  private void legerAction() {
    if (!moyenCBox.isSelected() && lourdCBox.isSelected()) {
      moyenCBox.setSelected(true);
    }
  }

  @FXML
  private void poidsHelp() {
    ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Poids des tissus",
        "Définissez une plage de poids à filtrer. La plage doit être continue. Léger = inférieur à " + df.format(
            margeHauteLeger) + ", Moyen = entre " + df.format(margeBasseMoyen) + " et " + df.format(margeHauteMoyen)
            + ", Lourd = supérieur à " + df.format(margeBasseLourd));
  }

  @FXML
  private void utilisableHelp() {
    ShowAlert.information(initializer.getPrimaryStage(), "Aide", "Tissu utilisable",
        "Définit si la recherche doit porter sur la quantité de tissu en stock (\"Utilisable\" décoché) ou sur la quantité de tissu disponible (\"Utilisable\" coché)");
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

}
